package com.rui.service;

import com.rui.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.form.OrderForm;
import com.rui.vo.BarDataVO;
import com.rui.vo.StackedVO;

import java.util.List;
import java.util.Map;

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
    public BarDataVO createBarData();
    public Map<String, List> createBaseLineData();
    public StackedVO createStackedData();
}
