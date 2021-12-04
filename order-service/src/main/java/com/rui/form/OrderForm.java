package com.rui.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

//后端给前端传的数据-VO
//前端传给后端的表单-Form
@Data
public class OrderForm {
    //数据校验（Spring-MVC提供）
    @NotEmpty(message = "买家姓名不能为空")
    private String name;
    @NotEmpty(message = "买家电话不能为空")
    private String phone;
    @NotEmpty(message = "收货地址不能为空")
    private String address;
    @NotNull(message = "买家ID不能为空")
    private Integer id;
    @NotEmpty(message = "商品信息不能空")
    private List<ProductForm> items;
}
