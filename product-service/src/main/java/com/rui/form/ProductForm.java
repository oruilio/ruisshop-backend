package com.rui.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductForm {
    @NotNull(message = "商品分类不能为空")
    private Integer categoryType;
    @NotEmpty(message = "商品描述不能为空")
    private String productDescription;
    @NotEmpty(message = "商品图标不能为空")
    private String productIcon;
    @NotEmpty(message = "商品名称不能为空")
    private String productName;
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;
    @NotNull(message = "商品状态不能为空")
    private Integer productStatus;
    @NotNull(message = "商品库存不能为空")
    private Integer productStock;
}