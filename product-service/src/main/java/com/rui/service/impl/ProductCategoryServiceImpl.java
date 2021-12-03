package com.rui.service.impl;

import com.rui.service.ProductCategoryService;
import com.rui.entity.ProductCategory;
import com.rui.mapper.ProductCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.vo.ProductCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    //先查询所有分类，再查询分类下对应的商品
    @Override
    public List<ProductCategoryVO> findAllProductCategoryVO() {
        //获取所有分类
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setName(productCategory.getCategoryName());
            productCategoryVO.setType(productCategory.getCategoryType());
            productCategoryVOList.add(productCategoryVO);
        }

        return productCategoryVOList;
    }
}
