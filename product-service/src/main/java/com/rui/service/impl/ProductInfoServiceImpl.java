package com.rui.service.impl;

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
}
