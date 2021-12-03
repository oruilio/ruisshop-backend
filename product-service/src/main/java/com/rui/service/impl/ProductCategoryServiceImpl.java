package com.rui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.entity.ProductInfo;
import com.rui.mapper.ProductInfoMapper;
import com.rui.service.ProductCategoryService;
import com.rui.entity.ProductCategory;
import com.rui.mapper.ProductCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.vo.ProductCategoryVO;
import com.rui.vo.ProductInfoVO;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private ProductInfoMapper productInfoMapper;

    //先查询所有分类，再查询分类下对应的商品
    //相当于将两张数据表（productCategory和productInfo）整理成API：/buyer/product所需的数据格式
    @Override
    public List<ProductCategoryVO> findAllProductCategoryVO() {
        //1.获取所有分类
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setName(productCategory.getCategoryName());
            productCategoryVO.setType(productCategory.getCategoryType());

            //2.获取具体good信息
            QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category_type", productCategory.getCategoryType());
            List<ProductInfo> productInfoList = this.productInfoMapper.selectList(queryWrapper);
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo: productInfoList){
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo, productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productCategoryVO.setGoods(productInfoVOList);

            productCategoryVOList.add(productCategoryVO);
        }

        return productCategoryVOList;
    }
}
