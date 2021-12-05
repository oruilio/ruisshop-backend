package com.rui.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.entity.OrderMaster;
import com.rui.service.OrderMasterService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("//orderDetail")
public class SellerOrderController {

    @Autowired
    OrderMasterService orderMasterService;

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
}

