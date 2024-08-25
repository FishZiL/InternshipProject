package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.mapper.CommuinityMapper;
import com.cq.cd.mapper.UserMapper;
import com.cq.cd.pojo.Community;
import com.cq.cd.pojo.User;
import com.cq.cd.service.CommunityService;
import com.cq.cd.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommuinityMapper, Community>implements CommunityService {
    @Autowired
    public CommuinityMapper commuinityMapper;
    @Autowired
    public UserMapper userMapper;
    public String CreateCommunity(Community community, String name,String username) {

        QueryWrapper<User> wrapperU = new QueryWrapper<User>().eq("username",username);
        User userE=userMapper.selectOne(wrapperU);
        if(userE == null){
            return "用户不存在";
        }
        community.setName(name);
        community.setCategoryid(1);
        community.setCreatedby(userE.getUid());
        QueryWrapper<Community> wrapper = new QueryWrapper<Community>().eq("name",community.getName());
        Community communityE=commuinityMapper.selectOne(wrapper);
        if (communityE == null){
            if ("".equals(community.getName())){
                return "社区名为空";
            }
            else {
                commuinityMapper.insert(community);
                return "success";
            }
        }
        return "社区已被注册";
    }
//    public String registerService(User user, String uname, String password) {
//        user.setUsername(uname);
//        //用工具类的md5进行加密
//        user.setUserpassword(Md5.md5(Md5.Wukong,password));
//        QueryWrapper<User> wrapper = new QueryWrapper<User>()
//                .eq("username",user.getUsername());
//        User userE=userMapper.selectOne(wrapper);
//        String Nonepwd=Md5.md5(Md5.Wukong,"");
//        if(userE==null){
//            if(Nonepwd.equals(user.getUserpassword())){
//                return "密码为空";
//            }
//            else if("".equals(user.getUsername())){
//                return "用户名为空";
//            }
//            else{
//                userMapper.insert(user);
//                return "success";
//            }
//        }
//        return "此用户已经被注册";
//    }
}
