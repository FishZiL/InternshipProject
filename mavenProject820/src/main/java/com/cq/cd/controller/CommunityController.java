package com.cq.cd.controller;

import com.cq.cd.mapper.CommuinityMapper;
import com.cq.cd.pojo.Community;
import com.cq.cd.service.CommunityService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/communities")
public class CommunityController {
    @Autowired
    public CommunityService communityService;
    @Autowired
    private CommuinityMapper commuinityMapper;
    @RequestMapping("/join")
    public Result create(@RequestParam (value = "name") String Cname ,@RequestParam(value = "username") String username){
        Community community =new Community();
        String CommunityCreate = communityService.CreateCommunity(community,Cname,username);
        if(CommunityCreate=="success"){
            return ResultUtil.success("创建社区成功");
        }
        else {
            return ResultUtil.error(CommunityCreate);
        }
    }
}
