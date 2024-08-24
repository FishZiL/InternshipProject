package com.cq.cd.vo;

import lombok.Data;

@Data
public class UserVo {
    private String username;

    private String password;

    private String email;
    //验证码
    private String code;
}

