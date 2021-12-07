package com.rui.vo;

import lombok.Data;

@Data
public class AdminVO {
    private Integer adminId;
    private String username;
    private String password;
    private String imgUrl;
    private String name;
    private String token;
}
