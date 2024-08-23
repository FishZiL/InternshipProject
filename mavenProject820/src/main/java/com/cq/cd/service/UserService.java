package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.pojo.User;

public interface UserService extends IService<User> {
    public String loginService(String uname, String pwd);
    public String registerService(User user, String uname, String pwd);
    public String loginbyemail(String email,String pwd);
    public String registerByEmail(User user,String email, String pwd);
}
