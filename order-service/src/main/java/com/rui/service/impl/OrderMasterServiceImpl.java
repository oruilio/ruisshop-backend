package com.rui.service.impl;

import com.rui.entity.OrderMaster;
import com.rui.form.OrderForm;
import com.rui.mapper.OrderMasterMapper;
import com.rui.service.OrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public Boolean create(OrderForm orderForm) {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName(orderForm.getName());
        orderMaster.setBuyerPhone(orderForm.getPhone());
        orderMaster.setBuyerAddress(orderForm.getAddress());
        orderMaster.setBuyerOpenid(orderForm.getId());
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMaster.setOrderAmount(new BigDecimal(10));

        int insert = this.orderMasterMapper.insert(orderMaster);
        return insert==1;
    }
}
