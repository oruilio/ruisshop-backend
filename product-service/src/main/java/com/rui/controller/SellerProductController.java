package com.rui.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.entity.ProductCategory;
import com.rui.entity.ProductInfo;
import com.rui.exception.ShopException;
import com.rui.form.ProductForm;
import com.rui.mapper.ProductCategoryMapper;
import com.rui.result.ResponseEnum;
import com.rui.service.ProductCategoryService;
import com.rui.service.ProductInfoService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.ProductCategoryVO;
import com.rui.vo.ResultVO;
import com.rui.vo.SellerProductVO;
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

    @Autowired
    ProductCategoryMapper productCategoryMapper;

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

    //查询商品所有商品--分页
    //创建返回数据SellerProductVO
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page,
                         @PathVariable("size") Integer size){
        //设置分页信息
        Page<ProductInfo> page1 = new Page<>(page, size);
        //从数据库中查询需要呈现的页面
        Page<ProductInfo> selectPage= this.productInfoService.page(page1, null);
        //获取呈现页面上的所有记录的具体数据
        List<ProductInfo> records = selectPage.getRecords();

        //对呈现的数据vo进行赋值
        List<SellerProductVO> voList = new ArrayList<>();
        for (ProductInfo record : records) {
            SellerProductVO vo = new SellerProductVO();
            BeanUtils.copyProperties(record, vo);
            if (record.getProductStatus() == 1) {
                vo.setStatus(true);
            }
            String nameByType = this.productCategoryMapper.getNameByType(record.getCategoryType());
            vo.setCategoryName(nameByType);
            voList.add(vo);
        }

        //封装返回给前端的数据
        Map<String,Object> map = new HashMap<>();
        map.put("content", voList);      //给前端返回vo
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return ResultVOUtil.success(map);
    }

}

