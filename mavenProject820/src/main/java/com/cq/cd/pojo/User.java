package com.cq.cd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user")
@Data
public class User {

    private long uid;
    @TableField(value = "username")
    private String username;
    @TableField(value = "passwordhash")
    private String userpassword;
    @TableField(value = "email")
    private String useremail;

    private String securityquestion;
    //数据库中只存储安全问题答案的MD5加密方式
    @TableField(value = "securityanswerhash")
    private String securityanswer;
    private String avatarurl;
    private String bio;


}
