package com.rui.mapper;

import com.rui.dto.BarDTO;
import com.rui.dto.BaseLineDTO;
import com.rui.entity.OrderMaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {

    public Boolean cancel(Integer buyerId, String orderId);
    public Boolean finish(String orderId);
    public Boolean pay(Integer buyerId,String orderId);

    public Boolean updateStatusByOrderId(String orderId,Integer status);

    public List<BarDTO> bar();
    public List<BaseLineDTO> baseLine();
}
