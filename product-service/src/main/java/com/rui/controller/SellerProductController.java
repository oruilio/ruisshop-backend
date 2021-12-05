package com.rui.controller;


import com.rui.entity.ProductCategory;
import com.rui.entity.ProductInfo;
import com.rui.exception.ShopException;
import com.rui.form.ProductForm;
import com.rui.result.ResponseEnum;
import com.rui.service.ProductCategoryService;
import com.rui.service.ProductInfoService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ProductCategoryVO;
import com.rui.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    ProductInfoService productInfoService;

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

    //添加商品
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody ProductForm productForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.PRODUCT_EMPTY.getMsg());
        }

        //赋值
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo); //两者的属性名必须完全一致
        productInfo.setProductStatus(1);

        //添加到数据库中
        boolean save = this.productInfoService.save(productInfo);

        if(save) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

}

