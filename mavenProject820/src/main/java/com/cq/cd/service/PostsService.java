package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.pojo.Posts;

public interface PostsService extends IService<Posts> {
    public String CreatePosts(Posts posts,Integer communityid,String username,String title,String content);
}

