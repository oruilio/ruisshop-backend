package com.rui.service.impl;

import com.rui.dto.BarDTO;
import com.rui.dto.BaseLineDTO;
import com.rui.entity.OrderDetail;
import com.rui.entity.OrderMaster;
import com.rui.entity.ProductInfo;
import com.rui.feign.ProductFeign;
import com.rui.form.OrderForm;
import com.rui.form.ProductForm;
import com.rui.mapper.OrderDetailMapper;
import com.rui.mapper.OrderMasterMapper;
import com.rui.service.OrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.util.EChartsColorUtil;
import com.rui.vo.BarDataVO;
import com.rui.vo.BarStyleVO;
import com.rui.vo.StackedInnerVO;
import com.rui.vo.StackedVO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public String create(OrderForm orderForm) {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName(orderForm.getName());
        orderMaster.setBuyerPhone(orderForm.getPhone());
        orderMaster.setBuyerAddress(orderForm.getAddress());
        orderMaster.setBuyerOpenid(orderForm.getId());
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        //计算订单总价：
        List<ProductForm> items = orderForm.getItems();
        BigDecimal amount = new BigDecimal(0);
        for(ProductForm item: items){
            Integer productId = item.getProductId();
            BigDecimal price = this.productFeign.findPriceById(productId);
            BigDecimal multiply = price.multiply(new BigDecimal(item.getProductQuantity()));
            amount=amount.add(multiply);
        }
        orderMaster.setOrderAmount(amount);

        //更新
        int insert = this.orderMasterMapper.insert(orderMaster);

        items = orderForm.getItems();
        for (ProductForm item : items) {
            //存储订单详情
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductQuantity(item.getProductQuantity());
            ProductInfo productInfo = this.productFeign.findById(item.getProductId());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductId(productInfo.getProductId());
            orderDetail.setOrderId(orderMaster.getOrderId());  //所以存订单详情要放在order存入数据库后
            this.orderDetailMapper.insert(orderDetail);
        }

        //将订单信息存入DB的同时也存入MQ，VUE前端工程可以通过mq-service访问MQ获取信息实现异步通信
        this.rocketMQTemplate.convertAndSend("newOrder", "新订单");

        return orderMaster.getOrderId();
    }

    //创建显示bar所需要的数据类型
    @Override
    public BarDataVO createBarData() {
        //从数据库中取出数据
        List<BarDTO> barDTOList = this.orderMasterMapper.bar();

        //创建返回数据类型
        BarDataVO barDataVO = new BarDataVO();

        //赋值
        List<String> names =  new ArrayList<>();
        List<BarStyleVO> values = new ArrayList<>();
        for(BarDTO barDTO: barDTOList){
            names.add(barDTO.getName());
            BarStyleVO barStyleVO = new BarStyleVO();
            barStyleVO.setValue(barDTO.getValue());
            barStyleVO.setItemStyle(EChartsColorUtil.createItemStyle(barDTO.getValue()));
            values.add(barStyleVO);
        }
        barDataVO.setValues(values);
        barDataVO.setNames(names);

        return barDataVO;
    }

    @Override
    public Map<String, List> createBaseLineData() {
        List<BaseLineDTO> baseLineDTOS = this.orderMasterMapper.baseLine();
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (BaseLineDTO baseLineDTO : baseLineDTOS) {
            names.add(baseLineDTO.getDate());
            values.add(baseLineDTO.getValue());
        }
        Map<String,List> map = new HashMap<>();
        map.put("names", names);
        map.put("values", values);
        return map;
    }


    //-取出所有商品名称
    //-取出有销售记录的日期
    //-将所有商品和有销售记录的日期对应，计算当日该商品的总销售额再一一对应
    //折线对应商品，横坐标对应有销售记录的日期，纵坐标对应该日期当前商品销售总数
    @Override
    public StackedVO createStackedData() {
        StackedVO stackedVO = new StackedVO();
        List<String> names = this.orderMasterMapper.findAllNames();
        List<String> dates = this.orderMasterMapper.findAllDates();
        List<StackedInnerVO> datas = new ArrayList<>();
        for (String name : names) {
            List<Integer> list = this.orderMasterMapper.findDatas(name);
            StackedInnerVO stackedInnerVO = new StackedInnerVO();
            stackedInnerVO.setName(name);
            stackedInnerVO.setData(list);
            datas.add(stackedInnerVO);
        }
        stackedVO.setNames(names);
        stackedVO.setDates(dates);
        stackedVO.setDatas(datas);
        return stackedVO;
    }

}
