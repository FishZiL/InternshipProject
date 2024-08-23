package com.cq.cd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user")
@Data
public class User {

    private long uid;
    @TableField(value = "uname")
    private String userName;
    @TableField(value = "password")
    private String userPassword;
    @TableField(value = "uemail")
    private String userEmail;
}
