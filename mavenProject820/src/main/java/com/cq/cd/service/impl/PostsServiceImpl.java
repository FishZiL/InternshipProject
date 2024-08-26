package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.mapper.CommuinityMapper;
import com.cq.cd.mapper.PostsMapper;
import com.cq.cd.mapper.UserMapper;
import com.cq.cd.pojo.Community;
import com.cq.cd.pojo.Posts;
import com.cq.cd.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {
    @Autowired
    public PostsMapper postsMapper;

    @Autowired
    public CommuinityMapper commuinityMapper;
    @Autowired
    public UserMapper userMapper;

    public String CreatePosts(Posts posts,Integer communityid,Integer authorid ,String title,String content){
        //社区是否存在
        QueryWrapper<Community> wrapperU = new QueryWrapper<Community>().eq("communityid",communityid);
        Community communityE=commuinityMapper.selectOne(wrapperU);
        if(communityE == null){
            return "社区不存在";
        }

        posts.setTitle(title);
        posts.setContent(content);
        posts.setCommunityid(communityid);
        posts.setAuthorid(authorid);

        QueryWrapper<Posts> wrapper = new QueryWrapper<Posts>().eq("title",posts.getTitle());
        Posts postsE=postsMapper.selectOne(wrapper);

        //标题是否为空
        if (postsE == null){
            if ("".equals(posts.getTitle())){
                return "标题为空";
            }
            else {
                postsMapper.insert(posts);
                return "success";
            }
        }
        return "已有重复标题";
    }
}
