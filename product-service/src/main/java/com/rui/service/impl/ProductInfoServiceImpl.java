package com.rui.service.impl;

import com.rui.entity.ProductInfo;
import com.rui.mapper.ProductInfoMapper;
import com.rui.service.ProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
