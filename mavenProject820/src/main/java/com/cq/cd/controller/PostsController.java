package com.cq.cd.controller;

import com.cq.cd.mapper.PostsMapper;
import com.cq.cd.pojo.Posts;
import com.cq.cd.service.PostsService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    public PostsService postsService;
    @Autowired
    private PostsMapper postsMapper;
    @RequestMapping("/create")
    public Result create(@RequestParam(value="communityid")Integer communityid,@RequestParam(value="userid")Integer authorid, @RequestParam(value="title")String title, @RequestParam(value="content")String content){
        Posts posts=new Posts();
        String msg= postsService.CreatePosts(posts,communityid,authorid,title,content);
        if(msg=="success"){
            return ResultUtil.success("发帖成功");
        }else{return ResultUtil.error(msg);
        }

    }
}