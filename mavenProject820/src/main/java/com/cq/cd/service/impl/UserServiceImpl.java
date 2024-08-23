package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.mapper.UserMapper;

import com.cq.cd.pojo.User;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


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
            String userpwd=user.getUserPassword();
            if(Md5.verify(Md5.Wukong,password,userpwd)){
                return "success";
            }else{
                return "密码错误";
            }
        }
        return "账号错误";
    }

    public String loginbyemail(String email,String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("uemail", email);
        User user=userMapper.selectOne(wrapper);
        if(user!=null){
            String userpwd=user.getUserPassword();
            if(Md5.verify(Md5.Wukong,password,userpwd)){
                return "success";
            }else{
                return "密码错误";
            }
        }
        return "邮箱地址错误";
    }

    public String registerService(User user,String uname,String password) {
        user.setUserName(uname);
        //用工具类的md5进行加密
        user.setUserPassword(Md5.md5(Md5.Wukong,password));
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("uname",user.getUserName());
        User userE=userMapper.selectOne(wrapper);
        String Nonepwd=Md5.md5(Md5.Wukong,"");
        if(userE==null){
            if(Nonepwd.equals(user.getUserPassword())){
                return "密码为空";
            }
            else if("".equals(user.getUserName())){
                return "用户名为空";
            }
            else{
                userMapper.insert(user);
                return "success";
            }
        }
        return "此用户已经被注册";
    }
    //通过邮箱注册
    public String registerByEmail(User user,String uemail,String password) {
        user.setUserEmail(uemail);
        //同样用工具类的md5进行加密
        user.setUserPassword(Md5.md5(Md5.Wukong,password));
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("uemail",user.getUserEmail());
        User userE=userMapper.selectOne(wrapper);
        String Nonepwd=Md5.md5(Md5.Wukong,"");
        if(userE==null){
            if(Nonepwd.equals(user.getUserPassword())){
                return "密码为空";
            }
            else if("".equals(user.getUserName())){
                return "邮箱地址为空";
            }
            else{
                userMapper.insert(user);
                return "success";
            }
        }
        return "此邮箱地址已经被注册";
    }

}
