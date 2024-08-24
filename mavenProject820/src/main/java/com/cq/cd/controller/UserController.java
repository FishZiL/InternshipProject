package com.cq.cd.controller;

import com.cq.cd.pojo.User;
import com.cq.cd.service.MailService;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;

import com.cq.cd.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private MailService mailService;
    //测试是否正常运行的hello
    @RequestMapping("/hello")
    public String hello() {
        return "Welcome to Our GameShare";
    }


    //普通账号密码登录
    @RequestMapping("/login")
    public Result login(@RequestParam String uname,@RequestParam String password){
        String msg = userService.loginService(uname, password);
        if(msg == "success"){
            return ResultUtil.success("登录成功");
        }
        return ResultUtil.error(msg);
    }
    //通过安全问题验证登录
    @RequestMapping("/login/security")
    public Result secLogin(@RequestParam String uname,@RequestParam String securityAnswer){
        String msg = userService.securityLogin(uname, securityAnswer);
        if(msg == "success"){
            return ResultUtil.success("登录成功");
        }
        return ResultUtil.error(msg);
    }
    //通过邮箱进行登录
    @RequestMapping("/loginByEmail")
    public Result registerByEmail(@RequestParam String uemail,@RequestParam String password){
        User user = new User();
        String msg = userService.loginbyemail(uemail,password);
        if(msg == "success"){
            return ResultUtil.success("邮箱登录成功");
        }
        return ResultUtil.error(msg);
    }


    //进行用户名和密码的注册
    @RequestMapping("/register")
    public Result register(@RequestParam String uname,@RequestParam String password){
        User user = new User();
        String msg = userService.registerService(user,uname,password);
        if(msg == "success"){
            return ResultUtil.success("注册成功");
        }
        return ResultUtil.error(msg);
    }

    //发送邮箱的验证码，验证码只能使用一次，错误需要重新发送
    @RequestMapping("/sendEmail")
    public Result sendEmail(@RequestParam(value = "email")String email, HttpSession session){
        boolean SendRes=mailService.sendMimeMail(email, session);
        if(SendRes){
            return ResultUtil.success("发送邮箱成功");
        }
        return ResultUtil.error("发送邮箱失败");
    }
    //邮箱注册需要验证码
    @RequestMapping("/registByemail")
    public Result registerbyemail(UserVo userVo, HttpSession session){
        String RegistRes=mailService.registered(userVo,session);
        if(RegistRes=="success"){
            return ResultUtil.success("邮箱地址注册成功");
        }
        return ResultUtil.error(RegistRes);
    }
    @RequestMapping("/1/update")
    public Result update(@RequestParam User user){
        String Update=userService.updateProfile(user);
        if(Update=="success"){
            return ResultUtil.success("更新个人信息成功");
        }
        return ResultUtil.error(Update);
    }
}
