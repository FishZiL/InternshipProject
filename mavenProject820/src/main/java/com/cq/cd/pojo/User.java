package com.cq.cd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user")
@Data
public class User {

    private long uid;
    @TableField(value = "username")
    private String userName;
    @TableField(value = "passwordHash")
    private String userPassword;
    @TableField(value = "email")
    private String userEmail;

    private String securityQuestion;
    //数据库中只存储安全问题答案的MD5加密方式
    @TableField(value = "securityAnswerHash")
    private String securityAnswer;
    private String avatarUrl;
    private String bio;


}
