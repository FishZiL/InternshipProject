package com.cq.cd.vo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserVo {
    @NotBlank(message = "请输入有效的邮箱地址")
    @Size(min = 6, max = 20, message = "邮箱地址长度在6-20")
    private String email;
    @NotBlank(message = "请输入密码")
    @Size(min = 6, max = 20, message = "注册密码长度在6-20")
    private String password;
    @NotBlank(message = "请再次输入密码")
    private String checkpassword;
    @NotBlank(message = "请输入你收到的验证码")
    private String code;
}

