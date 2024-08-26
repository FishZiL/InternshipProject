package com.cq.cd.register;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegister {
    @NotBlank(message = "请输入用户名")
    @Size(min = 2, max = 15, message = "注册用户名长度在2-15")
    private String username;
    @NotBlank(message = "请输入密码")
    @Size(min = 6, max = 20, message = "注册密码长度在6-20")
    private String password;
    @NotBlank(message = "请再次输入密码")
    private String checkpassword;
}
