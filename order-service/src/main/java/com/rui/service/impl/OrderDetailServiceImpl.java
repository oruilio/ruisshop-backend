package com.rui.service.impl;

import com.rui.entity.OrderDetail;
import com.rui.mapper.OrderDetailMapper;
import com.rui.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
