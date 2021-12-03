package com.rui.controller;


import com.rui.entity.ProductInfo;
import com.rui.service.ProductCategoryService;
import com.rui.service.ProductInfoService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@RestController  //可以返回JSON数据
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    //根据API接口文档从数据库中返回相应数据---创建ProductVO来规范返回数据框架
    //返回商品列表
    @GetMapping("/list")
    public ResultVO list(){
        return ResultVOUtil.success(this.productCategoryService.findAllProductCategoryVO());
    }

    //根据ID查询商品价格
    @GetMapping("/findPriceById/{id}")
    public BigDecimal findPriceById(@PathVariable("id") Integer id){
        return this.productInfoService.findPriceById(id);
    }

}

