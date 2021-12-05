package com.rui.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellerProductVO {
    @JsonProperty("id") //解析JSON时进行转换
    private Integer productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("stock")
    private Integer productStock;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;
    private String categoryName;
    private Boolean status;
}
