package com.rui.service;

import com.rui.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.form.OrderForm;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
public interface OrderMasterService extends IService<OrderMaster> {
    public String create(OrderForm orderForm);
}
