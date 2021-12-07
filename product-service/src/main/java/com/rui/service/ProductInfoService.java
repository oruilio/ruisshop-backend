package com.rui.service;

import com.rui.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.vo.ProductExcelVO;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

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
    public Boolean subStockById(Integer id, Integer quantity);
    public List<ProductExcelVO> productExcelVOList();
    public List<ProductInfo> excleToProductInfoList(InputStream inputStream);
}
