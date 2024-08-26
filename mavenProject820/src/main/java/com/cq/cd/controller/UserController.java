package com.cq.cd.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cq.cd.login.UserEmailLogin;
import com.cq.cd.login.UserLogin;
import com.cq.cd.login.UserQALogin;
import com.cq.cd.mapper.UserMapper;
import com.cq.cd.pojo.User;
import com.cq.cd.service.MailService;
import com.cq.cd.service.UserService;
import com.cq.cd.util.Md5;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import com.cq.cd.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cq.cd.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private MailService mailService;
    @Autowired
    private UserMapper userMapper;

    //测试是否正常运行的hello
    @RequestMapping("/hello")
    public String hello() {
        return "Welcome to Our GameShare";
    }

    //controller通过@RequestHeader来得到USER_NAME中存储的userId用户名
    //在重写的getheader方法中得到存储到claims中的username并将值传递给userName
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Result getUserInfo(@RequestHeader(value = USER_NAME) String userName) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
        if (user == null) {
            return ResultUtil.error("获取用户信息失败");
        }
        return ResultUtil.success("成功获取用户信息");
    }
    //普通账号密码登录
    @RequestMapping("/1/login")
    public Result login(@Valid UserLogin userlogin, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String token = userService.loginService(userlogin);
        if(ObjectUtils.isEmpty(token)){
            return ResultUtil.error("用户名或密码错误");
        }
        Map<String,String> map = new HashMap<>(16);
        map.put("token",token);
        return ResultUtil.success(map);
    }
    //通过安全问题验证登录
    @RequestMapping("/2/login")
    public Result secLogin(@Valid UserQALogin userQALogin, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String token = userService.securityLogin(userQALogin);
        if(ObjectUtils.isEmpty(token)){
            return ResultUtil.error("用户名或安全验证问题答案错误");
        }
        Map<String,String> map = new HashMap<>(16);
        map.put("token",token);
        return ResultUtil.success(map);
    }
    //通过邮箱进行登录
    @RequestMapping("/3/login")
    public Result LoginByEmail(@Valid UserEmailLogin userElogin, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String token = userService.loginbyemail(userElogin);
        if(ObjectUtils.isEmpty(token)){
            return ResultUtil.error("邮箱地址或密码错误");
        }
        Map<String,String> map = new HashMap<>(16);
        map.put("token",token);
        return ResultUtil.success(map);
    }


    //进行用户名和密码的注册
    @RequestMapping("/1/register")
    public Result register(@RequestParam (value = "name")String uname,@RequestParam (value = "password")String password){
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
    @RequestMapping("/2/regist")
    public Result registerbyemail(UserVo userVo, HttpSession session){
        String RegistRes=mailService.registered(userVo,session);
        if(RegistRes=="success"){
            return ResultUtil.success("邮箱地址注册成功");
        }
        return ResultUtil.error(RegistRes);
    }
    //
    @RequestMapping("/1/update")
    //头像Url，个人简介，安全问题的更新
    public Result update(User user){
        String Update=userService.updateProfile(user);
        if(Update=="success"){
            return ResultUtil.success("更新个人信息成功");
        }
        return ResultUtil.error(Update);
    }
    //更新密码
    @RequestMapping("/2/update")
    public Result updatepassword(@RequestParam(value = "username") String username,@RequestParam(value = "new") String newpassword,@RequestParam(value = "old") String oldpassword){
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username);
        User user=userMapper.selectOne(wrapper);
        if(Md5.verify(Md5.Wukong,oldpassword,user.getUserpassword())){
            String passwordHash=Md5.md5(Md5.Wukong,newpassword);
            UpdateWrapper<User> wrapperup = new UpdateWrapper<User>()
                    .eq("username",username);
            user.setUserpassword(passwordHash);
            userMapper.update(user,wrapperup);
            return ResultUtil.success("更新密码成功！");
        }
        return ResultUtil.error("用户密码错误，无法更新密码");
    }
    //更新安全问题答案，使用用户密码进行更新
    @RequestMapping("/3/update")
    public Result updateans(@RequestParam(value = "username") String username,@RequestParam(value = "new") String newans,@RequestParam(value = "pass") String password){
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username);
        User user=userMapper.selectOne(wrapper);
        if(Md5.verify(Md5.Wukong,password,user.getUserpassword())){
            String ansHash=Md5.md5(Md5.Wukong,newans);
            UpdateWrapper<User> wrapperup = new UpdateWrapper<User>()
                    .eq("username",username);
            user.setSecurityanswer(ansHash);
            userMapper.update(user,wrapperup);
            return ResultUtil.success("更新安全问题答案成功！");
        }
        return ResultUtil.error("密码错误，无法更新安全问题答案");
    }
    //用户搜索
    @RequestMapping("/search")
    public ArrayList search(@RequestParam(value = "username") String username){
        ArrayList list=new ArrayList();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username);
        User user=userMapper.selectOne(wrapper);
        list.add("用户编号："+user.getUid());
        list.add("用户昵称:"+user.getUsername());
        list.add("用户的邮箱地址:"+user.getUseremail());
        list.add("用户简介:"+user.getBio());
        return list;
    }
    //注销用户：删除用户在数据库中的所有数据
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "username") String username){
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username);
        int col=userMapper.delete(wrapper);
        if(col>0){
            return ResultUtil.success("删除成功");
        }
        return  ResultUtil.error("删除失败");
    }


}
