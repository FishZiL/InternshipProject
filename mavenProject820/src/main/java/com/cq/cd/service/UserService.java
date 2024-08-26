package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.login.UserEmailLogin;
import com.cq.cd.login.UserLogin;
import com.cq.cd.login.UserQALogin;
import com.cq.cd.pojo.User;

public interface UserService extends IService<User> {
    public String loginService(UserLogin userlogin);
    public String securityLogin(UserQALogin userQAlogin);
    public String loginbyemail(UserEmailLogin userEogin);
    public String registerService(User user, String uname, String pwd);
    public String generateRandomCode();
    public String showSecurityAns(User user);
    public String updateProfile(User user);
}
