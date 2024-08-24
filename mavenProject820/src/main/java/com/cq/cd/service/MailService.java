package com.cq.cd.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cq.cd.mapper.UserMapper;
import com.cq.cd.pojo.User;
import com.cq.cd.util.Md5;
import com.cq.cd.vo.UserVo;
import com.cq.cd.vo.UserVoToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class MailService {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String from;


    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code= userService.generateRandomCode();
            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);
            mailMessage.setText("您的验证码为：" + code + ",请勿泄露给他人。");
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(from);//自己的邮箱
            mailSender.send(mailMessage);//发送
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String registered(UserVo userVo, HttpSession session){
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");

        //获取表单中的提交的验证信息
        String voCode = userVo.getCode();
        //保存数据
        User user = UserVoToUser.toUser(userVo);
        //用wrapper筛选出是否该邮箱的信息
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("uemail",email);
        User userE=userMapper.selectOne(wrapper);
        //确认该邮箱地址是否有注册过
        if(userE==null){
            //如果email数据为空，或者不一致，注册失败
            if (email==null){
                return "邮箱地址为空，请重新注册";
            }
            else if("".equals(user.getUserpassword())){
                return "密码为空";
            }
            else if(!code.equals(voCode)){
                return "验证码错误，请确认后重新注册";
            }else{
                user.setUserpassword(Md5.md5(Md5.Wukong,user.getUserpassword()));
                //将数据写入数据库
                userMapper.insert(user);
                return "success";
            }
        }
        return "此邮箱地址已经被注册";
        //跳转成功页面
    }
}
