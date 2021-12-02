package com.rui.vo;

import lombok.Data;

//后端给前端返回数据的通用框架
@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;
}
