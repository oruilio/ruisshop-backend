package com.rui.controller;


import com.rui.entity.ProductInfo;
import com.rui.service.ProductCategoryService;
import com.rui.service.ProductInfoService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    //根据id查询商品价格
    @GetMapping("/findPriceById/{id}")
    public BigDecimal findPriceById(@PathVariable("id") Integer id){
        return this.productInfoService.findPriceById(id);
    }

    //通过id查询商品
    @GetMapping("findById/{id}")
    public ProductInfo findById(@PathVariable("id") Integer id){
        return this.productInfoService.getById(id);
    }

    //减库存
    @PutMapping("/subStockById/{id}/{quantity}")
    public Boolean subStockById(
            @PathVariable("id") Integer id,
            @PathVariable("quantity") Integer quantity){
        return this.productInfoService.subStockById(id, quantity);
    }
}

