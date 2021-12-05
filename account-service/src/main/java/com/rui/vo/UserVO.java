package com.rui.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer userId;
    private String mobile;
    private String password;
    private String token;
}