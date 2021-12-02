package com.rui.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

//统一处理所有错误信息
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    USERNAME_NULL(300,"用户不存在！"),
    PASSWORD_ERROR(301,"密码错误！"),
    LOGIN_ERROR(302,"登录信息失效！"),
    LOGIN_RECORD_ERROR(303,"登录日志添加失败！"),
    ORDER_CREATE_ERROR(304,"订单信息不能为空！"),
    PARAMETER_NULL(305,"参数为空！"),
    PRODUCT_PRICE_NULL(306,"商品价格为空！"),
    PRODUCT_STOCK_ERROR(307,"商品库存不足！"),
    UPDATE_PRODUCT_ERROR(308,"修改库存失败！"),
    ORDER_STATUS_ERROR(309,"订单状态异常！"),
    ORDER_STATUS_UPDATE_FAIL(310,"修改订单状态失败！"),
    ORDER_PAY_STATUS_ERROR(311,"订单支付状态异常！"),
    ORDER_PAY_FAIL(312,"订单支付失败！"),
    PRODUCT_EMPTY(313,"商品信息不能为空！"),
    PRODUCT_UPDATE_STATUS_ERROR(314,"修改商品状态失败！"),
    PRODUCT_UPDATE_ERROR(315,"修改商品失败！"),
    PRODUCT_CREATE_ERROR(316,"添加商品失败！"),
    SMS_SEND_ERROR(317,"短信发送失败！"),
    MOBILE_ERROR(318,"手机号格式错误！"),
    USER_INFO_NULL(319,"用户信息不能为空！"),
    USER_CODE_ERROR(320,"短信校验码错误！"),
    USER_MOBILE_EXIST(321,"手机号已被注册！"),
    USER_MOBILE_NULL(322,"手机号不存在！");

    private Integer code;
    private String msg;
}
