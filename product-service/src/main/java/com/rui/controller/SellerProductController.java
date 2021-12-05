package com.rui.controller;


import com.rui.entity.ProductCategory;
import com.rui.service.ProductCategoryService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ProductCategoryVO;
import com.rui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    ProductCategoryService productCategoryService;

    //查询所有商品分类
    //创建CategoryVO
    @GetMapping("/findAllProductCategory")
    public ResultVO findAllProductCategory(){
        //从数据库中获取数据，存入vo中
        List<ProductCategory> list = this.productCategoryService.list();
        List<ProductCategoryVO> voList = new ArrayList<>();
        for (ProductCategory productCategory : list) {
            ProductCategoryVO vo = new ProductCategoryVO();
            vo.setName(productCategory.getCategoryName());
            vo.setType(productCategory.getCategoryType());
            voList.add(vo);
        }

        //封装上层数据层content
        Map<String,List> map = new HashMap<>();
        map.put("content",voList);

        return ResultVOUtil.success(map);
    }

}

