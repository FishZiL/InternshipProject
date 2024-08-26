package com.cq.cd.vo;
import com.cq.cd.pojo.User;
public class UserVoToUser {

    public static User toUser(UserVo userVo) {
        User user = new User();
        user.setUserpassword(userVo.getPassword());
        user.setUseremail(userVo.getEmail());
        return user;
    }
}
