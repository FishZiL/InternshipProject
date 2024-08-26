package com.cq.cd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cq.cd.mapper.GameMapper;
import com.cq.cd.pojo.Game;
import com.cq.cd.pojo.User;
import com.cq.cd.service.GameService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/game")

public class GameController {
    @Autowired
    private GameService gameService;
    private GameMapper gameMapper;
    @RequestMapping("/create")
    public Result creatGame(Game game){
        String msg=gameService.CreateGame(game);
        if(msg=="success"){
            return ResultUtil.success("创建游戏信息成功");
        }
        return ResultUtil.error(msg);
    }
    @RequestMapping("/update")
    public Result updateGame(Game game){
        String msg=gameService.updateInfo(game);
        if(msg=="success"){
            return ResultUtil.success("更新游戏信息成功");
        }
        return ResultUtil.error(msg);
    }
    @RequestMapping("/delete")
    public Result deleteGame(Game game){
        String msg=gameService.deleteGame(game);
        if(msg=="success"){
            return ResultUtil.success("删除游戏成功");
        }
        return ResultUtil.error(msg);
    }

    @RequestMapping("/search")
    public ArrayList search(@RequestParam(value = "title") String title){
        ArrayList list = new ArrayList();
        QueryWrapper<Game> wrapper = new QueryWrapper<Game>().eq("title",title) ;
        Game game =gameMapper.selectOne(wrapper);
        list.add("游戏编号:"+game.getGameid());
        list.add("游戏名:"+game.getTitle());
        list.add("描述:"+game.getDescription());
        list.add("开发者"+game.getDeveloper());
        list.add("发行商:"+game.getPublisher());
        return list;
    }
}
