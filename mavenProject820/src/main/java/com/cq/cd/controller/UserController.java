package com.cq.cd.controller;

import com.cq.cd.pojo.User;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    @RequestMapping("/login")
    public Result login(@RequestParam String uname,@RequestParam String password){
        String msg = userService.loginService(uname, password);
        if(msg == "success"){
            return ResultUtil.success("登录成功");
        }
        return ResultUtil.error(msg);
    }

    @RequestMapping("/loginByEmail")
    public Result registerByEmail(@RequestParam String uemail,@RequestParam String password){
        User user = new User();
        String msg = userService.loginbyemail(uemail,password);
        if(msg == "success"){
            return ResultUtil.success("邮箱登录成功");
        }
        return ResultUtil.error(msg);
    }

    @RequestMapping("/register")
    public Result register(@RequestParam String uname,@RequestParam String password){
        User user = new User();
        String msg = userService.registerService(user,uname,password);
        if(msg == "success"){
            return ResultUtil.success("注册成功");
        }
        return ResultUtil.error(msg);
    }
    //邮箱注册需要验证码
    @RequestMapping("/registerByemail")
    public Result registerbyemail(@RequestParam String uemail,@RequestParam String password){
        User user = new User();
        String msg = userService.registerByEmail(user,uemail,password);
        if(msg == "success"){
            return ResultUtil.success("邮箱地址注册成功");
        }
        return ResultUtil.error(msg);
    }

}
