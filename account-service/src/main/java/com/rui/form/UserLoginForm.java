package com.rui.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserLoginForm {
    @NotEmpty(message = "电话不能为空")
    private String mobile;
    @NotEmpty(message = "密码不能为空")
    private String password;
}