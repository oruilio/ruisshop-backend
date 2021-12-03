package com.rui.vo;

import com.rui.entity.ProductInfo;
import lombok.Data;

import java.util.List;

//VO = view object
@Data
public class ProductCategoryVO {
    private String name;
    private Integer type;
    private List<ProductInfoVO> goods;
}
