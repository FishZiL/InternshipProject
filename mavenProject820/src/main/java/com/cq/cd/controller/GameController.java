package com.cq.cd.controller;


import com.cq.cd.pojo.Game;
import com.cq.cd.service.GameService;
import com.cq.cd.util.Result;
import com.cq.cd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

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
}
