package com.cq.cd.vo;
import com.cq.cd.pojo.User;
public class UserVoToUser {
    /**
     * 将表单中的对象转化为数据库中存储的用户对象（剔除表单中的code）
     * @param userVo
     * @return
     */
    public static User toUser(UserVo userVo) {
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setUserpassword(userVo.getPassword());
        user.setUseremail(userVo.getEmail());
        return user;
    }
}
