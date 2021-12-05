package com.rui.mapper;

import com.rui.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    public BigDecimal findPriceById(Integer id);
    public Integer findStockById(Integer id);
    public Integer updateStockById(Integer id, Integer stock);

    public Boolean updateStatusById(Integer id,Integer status);
}
