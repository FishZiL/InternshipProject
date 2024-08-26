package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.login.UserLogin;
import com.cq.cd.pojo.User;

public interface UserService extends IService<User> {
    public String loginService(UserLogin userlogin);
    public String securityLogin(String uname, String answer);
    public String registerService(User user, String uname, String pwd);
    public String loginbyemail(String email,String pwd);
    public String generateRandomCode();
    public String showSecurityAns(User user);
    public String updateProfile(User user);
}
