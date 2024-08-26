package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.controller.UserController;
import com.cq.cd.jwt.JwtUtil;
import com.cq.cd.login.UserEmailLogin;
import com.cq.cd.login.UserLogin;
import com.cq.cd.login.UserQALogin;
import com.cq.cd.mapper.UserMapper;

import com.cq.cd.pojo.User;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    public UserMapper userMapper;
    /*
    将username、password后；通过userMapper的selectone选出该User
    */
    public String loginService(UserLogin userlogin) {
        //初始化一个Token
        String Token=null;
        //先由wrapper判断传入的userName是否由与数据库匹配的(selectOne)
        try{
            User user=userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userlogin.getUsername()));
            boolean Right=Md5.verify(Md5.Wukong,userlogin.getPassword(),user.getUserpassword());
            if(!Right){
                throw new Exception("密码错误");
            }
            Token= JwtUtil.generateToken(String.valueOf(user.getUsername()));
        }
        catch(Exception e){
            log.warn("用户"+userlogin.getUsername()+"不存在或者密码验证失败");
        }
        return Token;
    }

    public String securityLogin(UserQALogin userQAlogin) {
        //与用户名和密码登录一致，都需要返回Token
        String Token=null;
        try {
            User user=userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userQAlogin.getUsername()));
            boolean Right=Md5.verify(Md5.Wukong,userQAlogin.getSecurityanswer(),user.getSecurityanswer());
            if(!Right){
                throw  new Exception("安全验证问题答案错误");
            }
            Token= JwtUtil.generateToken(String.valueOf(user.getUsername()));
        }
        catch(Exception e){
            log.warn("用户"+userQAlogin.getUsername()+"不存在或者安全验证问题答案错误");
        }
        return Token;
    }
    //将安全验证问题返回显示的函数
    public String showSecurityAns(User user){
        return user.getSecurityanswer();
    }

    //通过邮箱进行登录
    public String loginbyemail(UserEmailLogin userElogin) {
        String Token=null;
        try {
            User user=userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUseremail, userElogin.getUseremail()));
            boolean Right=Md5.verify(Md5.Wukong,userElogin.getPassword(),user.getUserpassword());
            if(!Right){
                throw  new Exception("密码错误");
            }
            Token= JwtUtil.generateToken(String.valueOf(user.getUsername()));
        }
        catch(Exception e){
            log.warn("用户邮箱地址"+userElogin.getUseremail()+"不存在或者密码错误");
        }
        return Token;
    }

    public String registerService(User user,String uname,String password) {
        user.setUsername(uname);
        //用工具类的md5进行加密
        user.setUserpassword(Md5.md5(Md5.Wukong,password));
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username",user.getUsername());
        User userE=userMapper.selectOne(wrapper);
        String Nonepwd=Md5.md5(Md5.Wukong,"");
        if(userE==null){
            if(Nonepwd.equals(user.getUserpassword())){
                return "密码为空";
            }
            else if("".equals(user.getUsername())){
                return "用户名为空";
            }
            else{
                userMapper.insert(user);
                return "success";
            }
        }
        return "此用户已经被注册";
    }
    public String updateProfile(User user){
        //头像Url，个人简介，安全问题的更新
        user.setAvatarurl(user.getAvatarurl());
        user.setBio(user.getBio());
        user.setSecurityquestion(user.getSecurityquestion());
        int update = userMapper.update(user,new QueryWrapper<User>().eq("username",user.getUsername()));
        if(update>0){
            return "success";
        }else return "更新失败";
    }

    //返回随机的六位验证码
    public String generateRandomCode(){
        // 字母和数字组合
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // 拆分每一个字符放到数组中
        String[] newStr = str.split("");
        StringBuilder builder = new StringBuilder();
        // 循环随机生成6为数字和字母组合
        for (int i = 0; i < 6; i++){
            // 通过循环6次，为stringBuilder追加内容，内容为随机数✖62，取整
            builder.append(newStr[(int)(Math.random() * 62)]);
        }
        return builder.toString();
    }

}
