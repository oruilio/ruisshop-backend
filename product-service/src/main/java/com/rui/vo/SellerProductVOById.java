package com.rui.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class SellerProductVOById {
    @JsonProperty("id")
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
    private Map<String,Integer> category;
    private Boolean status;
}
