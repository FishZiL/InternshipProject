package com.cq.cd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cq.cd.mapper.PostsMapper;
import com.cq.cd.pojo.Posts;
import com.cq.cd.pojo.User;
import com.cq.cd.service.PostsService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    public PostsService postsService;
    @Autowired
    private PostsMapper postsMapper;

    @RequestMapping("/create")
    public Result create(Integer communityid, @RequestParam(value = "userid") Integer authorid, String title, String content) {
        Posts posts = new Posts();
        String msg = postsService.CreatePosts(posts, communityid, authorid, title, content);
        if (msg == "success") {
            return ResultUtil.success("发帖成功");
        } else {
            return ResultUtil.error(msg);
        }
    }

    @RequestMapping("/search")
    public ArrayList search(String title) {
        ArrayList list = new ArrayList();

        QueryWrapper<Posts> wrapper = new QueryWrapper<Posts>().eq("title", title);
        Posts posts = postsMapper.selectOne(wrapper);

        if (posts == null) {
            list.add("您所查询的帖子不存在");
        } else {
            list.add("帖子ID:" + posts.getPostid());
            list.add("帖子标题:" + posts.getTitle());
            list.add("评论内容:" + posts.getContent());
            list.add("作者ID：" + posts.getAuthorid());
        }
        return list;
    }

    @RequestMapping("/search2")
    public Posts search2(String title) {

        QueryWrapper<Posts> wrapper = new QueryWrapper<Posts>().eq("title", title);
        Posts posts = postsMapper.selectOne(wrapper);
        if(posts==null){
            return null;
        }else return posts;
    }

    @RequestMapping("/delete")
    public Result delete( String title,Integer userid){
        QueryWrapper<Posts> wrapper = new QueryWrapper<Posts>().eq("title", title).eq("authorid", userid);
        int col=postsMapper.delete(wrapper);
        if(col>0){
            return ResultUtil.success("删帖成功");
        }
        return  ResultUtil.error("删帖失败");
    }
}
