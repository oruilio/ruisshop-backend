package com.rui.service;

import com.rui.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
public interface ProductInfoService extends IService<ProductInfo> {
    //IService中提供的findById会返回该id的所有信息，效率较低
    //个性化方法可以提高效率
    public BigDecimal findPriceById(Integer id);

}
