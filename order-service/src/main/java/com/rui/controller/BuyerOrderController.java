package com.rui.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.entity.OrderDetail;
import com.rui.entity.OrderMaster;
import com.rui.exception.ShopException;
import com.rui.form.OrderForm;
import com.rui.mapper.OrderMasterMapper;
import com.rui.result.ResponseEnum;
import com.rui.service.OrderDetailService;
import com.rui.service.OrderMasterService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.OrderDetailVO;
import com.rui.vo.OrderMasterVO;
import com.rui.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterMapper orderMasterMapper;

    //创建订单
    //创建一个实体类Form对应从前端传到后端的数据
    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.ORDER_CREATE_ERROR.getMsg());
        }
        String orderId = this.orderMasterService.create(orderForm);
        if(orderId!=null) {
            Map<String,String> map = new HashMap<>();
            map.put("orderId",orderId);
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.fail(null);
    }

    //返回订单列表
    @GetMapping("/list/{buyerId}/{page}/{size}")
    public ResultVO list(
            @PathVariable("buyerId") Integer buyerId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ){
        Page<OrderMaster> pageRule = new Page<>(page, size);
        QueryWrapper<OrderMaster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_openid", buyerId);
        Page<OrderMaster> selectPage = this.orderMasterService.page(pageRule, queryWrapper);
        return ResultVOUtil.success(selectPage.getRecords());
    }

    //查询订单详情
    @GetMapping("/detail/{buyerId}/{orderId}")
    public ResultVO detail(
            @PathVariable("buyerId") Integer buyerId,
            @PathVariable("orderId") String orderId
    ){
        //从数据库中通过orderId，buyerId获取相应order master信息
        QueryWrapper<OrderMaster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_openid", buyerId);
        queryWrapper.eq("order_id", orderId);
        OrderMaster one = this.orderMasterService.getOne(queryWrapper);
        //赋值到orderMasterVO中
        OrderMasterVO orderMasterVO = new OrderMasterVO();
        BeanUtils.copyProperties(one, orderMasterVO);

        //从数据库中查询order_detail，所有order_id下的信息
        QueryWrapper<OrderDetail> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order_id", orderId);
        List<OrderDetail> list = this.orderDetailService.list(queryWrapper1);
        //注入orderDetailVOList
        List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            BeanUtils.copyProperties(orderDetail, orderDetailVO);
            orderDetailVOList.add(orderDetailVO);
        }

        orderMasterVO.setOrderDetailList(orderDetailVOList);
        return ResultVOUtil.success(orderMasterVO);
    }

    //取消订单 订单状态为2
    @PutMapping("/cancel/{buyerId}/{orderId}")
    public ResultVO cancel(
            @PathVariable("buyerId") Integer buyerId,
            @PathVariable("orderId") String orderId
    ){
        Boolean cancel = this.orderMasterMapper.cancel(buyerId, orderId);
        if(cancel) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //完成订单，订单状态为1
    @PutMapping("/finish/{orderId}")
    public ResultVO finish(
            @PathVariable("orderId") String orderId
    ){
        Boolean cancel = this.orderMasterMapper.finish( orderId);
        if(cancel) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //支付订单，支付状态为1
    @PutMapping("/pay/{buyerId}/{orderId}")
    public ResultVO pay(
            @PathVariable("buyerId") Integer buyerId,
            @PathVariable("orderId") String orderId
    ){
        Boolean cancel = this.orderMasterMapper.pay(buyerId, orderId);
        if(cancel) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

}

