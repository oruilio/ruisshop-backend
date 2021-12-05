package com.rui.service.impl;

import com.rui.exception.ShopException;
import com.rui.mapper.ProductCategoryMapper;
import com.rui.result.ResponseEnum;
import com.rui.service.ProductInfoService;
import com.rui.entity.ProductInfo;
import com.rui.mapper.ProductInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.vo.ProductExcelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

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

    //实质上是将 List<ProductInfo> 转化为 List<ProductExcelVO>
    @Override
    public List<ProductExcelVO> productExcelVOList() {
        List<ProductInfo> productInfoList = this.productInfoMapper.selectList(null);
        List<ProductExcelVO> result = new ArrayList<>();
        //调整数据，逐一赋值
        for (ProductInfo productInfo : productInfoList) {
            ProductExcelVO vo = new ProductExcelVO();
            BeanUtils.copyProperties(productInfo, vo);
            vo.setProductStatus("下架");
            if(productInfo.getProductStatus() == 1) vo.setProductStatus("上架");
            String nameByType = this.productCategoryMapper.getNameByType(productInfo.getCategoryType());
            vo.setCategoryName(nameByType);
            result.add(vo);
        }
        return result;
    }
}
