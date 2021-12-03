package com.rui.service.impl;

import com.rui.exception.ShopException;
import com.rui.result.ResponseEnum;
import com.rui.service.ProductInfoService;
import com.rui.entity.ProductInfo;
import com.rui.mapper.ProductInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public BigDecimal findPriceById(Integer id) {
        return this.productInfoMapper.findPriceById(id);
    }

    @Override
    public Boolean subStockById(Integer id, Integer quantity) {
        Integer stock = this.productInfoMapper.findStockById(id);
        Integer result = stock - quantity;
        if(result < 0) throw new ShopException(ResponseEnum.PRODUCT_STOCK_ERROR.getMsg());
        Integer integer = this.productInfoMapper.updateStockById(id, result);
        return integer==1;
    }
}
