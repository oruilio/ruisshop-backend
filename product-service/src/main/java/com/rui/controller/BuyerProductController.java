package com.rui.controller;


import com.rui.service.ProductCategoryService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    //根据API接口文档从数据库中返回相应数据---创建ProductVO来规范返回数据框架
    @GetMapping("/list")
    public ResultVO list(){
        return ResultVOUtil.success(this.productCategoryService.findAllProductCategoryVO());
    }

}

