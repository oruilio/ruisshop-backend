package com.rui.mapper;

import com.rui.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@Repository
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    public String getNameByType(Integer type);
}
