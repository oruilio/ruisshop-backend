package com.rui.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class AdminForm {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
