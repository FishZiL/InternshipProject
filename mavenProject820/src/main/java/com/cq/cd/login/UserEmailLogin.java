package com.cq.cd.login;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserEmailLogin {
    @NotBlank(message = "邮箱地址不能为空")
    @Size(min = 6, max = 20, message = "邮箱地址长度在6-20")
    private String useremail;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    private String password;
}
