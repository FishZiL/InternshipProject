package com.cq.cd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@TableName("user")
@Data
public class User {
    @TableId(value = "userid")
    private int uid;
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
