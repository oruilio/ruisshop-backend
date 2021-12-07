package com.rui.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.entity.OrderMaster;
import com.rui.mapper.OrderMasterMapper;
import com.rui.service.OrderMasterService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单详情表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    OrderMasterMapper orderMasterMapper;

    //查询所有订单--分页
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ){
        //分页配置
        Page<OrderMaster> page1 = new Page<>(page, size);
        //获取数据库中当前页信息
        Page<OrderMaster> selectPage = this.orderMasterService.page(page1);

        //返回数据和OrderMaster实体类数据一致，直接获取使用map封装上层数据
        Map<String,Object> map = new HashMap<>();
        map.put("content", selectPage.getRecords());
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return ResultVOUtil.success(map);
    }

    //取消订单
    //实质是修改订单状态为2
    @PutMapping("/cancel/{orderId}")
    public ResultVO cancel(@PathVariable("orderId") String orderId){
        Boolean aBoolean = this.orderMasterMapper.updateStatusByOrderId(orderId, 2);
        if(aBoolean) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //完成订单
    //实质是修改订单状态为1
    @PutMapping("/finish/{orderId}")
    public ResultVO finish(@PathVariable("orderId") String orderId){
        Boolean aBoolean = this.orderMasterMapper.updateStatusByOrderId(orderId, 1);
        if(aBoolean) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }


    //返回呈现柱状图所需数据
    @GetMapping("/barSale")
    public ResultVO barSale(){
        return ResultVOUtil.success(this.orderMasterService.createBarData());
    }

    //返回呈现基础折线图所需数据
    @GetMapping("/basicLineSale")
    public ResultVO basicLineSale(){
        return ResultVOUtil.success(this.orderMasterService.createBaseLineData());
    }

    //获取所有商品在有销售记录的日期中的销售总额折线图
    @GetMapping("/stackedLineSale")
    public ResultVO stackedLineSale(){
        return ResultVOUtil.success(this.orderMasterService.createStackedData());
    }
}

