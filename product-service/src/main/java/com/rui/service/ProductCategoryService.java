package com.rui.service;

import com.rui.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> findAllProductCategoryVO();
}
