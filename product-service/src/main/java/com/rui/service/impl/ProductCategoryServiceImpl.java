package com.rui.service.impl;

import com.rui.entity.ProductCategory;
import com.rui.mapper.ProductCategoryMapper;
import com.rui.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
