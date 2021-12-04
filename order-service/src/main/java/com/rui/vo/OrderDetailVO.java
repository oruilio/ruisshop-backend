package com.rui.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailVO {
    private String detailId;
    private String orderId;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productIcon;
}
