package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.controller.UserController;
import com.cq.cd.mapper.UserMapper;

import com.cq.cd.pojo.User;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Md5;
import com.cq.cd.util.Updatefiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    public UserMapper userMapper;

    public String loginService(String username, String password) {
        //先由wrapper判断传入的userName是否由与数据库匹配的(selectOne)
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("uname", username);
        User user=userMapper.selectOne(wrapper);
        //user对象为空，直接输出账号错误；否则进一步验证密码
        if(user!=null){
            //getUserPassword得到的密码是加密后的结果；进行验证:调用verify函数
            String userpwd=user.getUserpassword();
            if(Md5.verify(Md5.Wukong,password,userpwd)){
                return "success";
            }else{
                return "密码错误";
            }
        }
        return "账号错误";
    }

    public String securityLogin(String username, String answer) {
        //先由wrapper判断传入的userName是否由与数据库匹配的(selectOne)
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username);
        User user=userMapper.selectOne(wrapper);
        //user对象为空，直接输出账号错误；否则进一步验证密码
        if(user!=null){
            //getSecurityAnswer得到的答案是加密后的结果；进行验证:调用verify函数
            String userans=user.getSecurityanswer();
            if(Md5.verify(Md5.Wukong,answer,userans)){
                return "success";
            }else{
                return "安全问题验证失败";
            }
        }
        return "用户名错误";
    }
    //将安全验证问题返回显示的函数
    public String showSecurityAns(User user){
        return user.getSecurityanswer();
    }

    public String loginbyemail(String email,String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("uemail", email);
        User user=userMapper.selectOne(wrapper);

        if(user!=null){
            String userpwd=user.getUserpassword();
            if(Md5.verify(Md5.Wukong,password,userpwd)){
                return "success";
            }else{
                return "密码错误";
            }
        }
        return "邮箱地址错误";
    }

    public String registerService(User user,String uname,String password) {
        user.setUsername(uname);
        //用工具类的md5进行加密
        user.setUserpassword(Md5.md5(Md5.Wukong,password));
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("uname",user.getUsername());
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
