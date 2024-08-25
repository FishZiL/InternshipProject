package com.cq.cd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.cd.mapper.GameMapper;
import com.cq.cd.pojo.Game;
import com.cq.cd.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {
    @Autowired
    public GameMapper gameMapper;
    //添加游戏相关信息，包括游戏名称、游戏描述、开发者、发行商、发布日期
    public String CreateGame(Game game){
        if(game.getTitle()==null||game.getTitle().equals("")){return "游戏名为空，无法注册";}
        game.setTitle(game.getTitle());
        game.setDescription(game.getDescription());
        game.setDeveloper(game.getDeveloper());
        game.setPublisher(game.getPublisher());
        game.setReleasedate(game.getReleasedate());
        QueryWrapper<Game> wrapper = new QueryWrapper<Game>().eq("title",game.getTitle());
        Game gameE=gameMapper.selectOne(wrapper);
        if(gameE==null){
            if(game.getDeveloper()==null||game.getDeveloper().equals("")){
                return "需注明开发者信息";
            }
            else if(game.getPublisher()==null||game.getPublisher().equals("")){
                return "需注明发行商信息";
            }
            else if(game.getReleasedate()==null||game.getReleasedate().equals("")){
                return "需要提供发布日期";
            }
            else{
                gameMapper.insert(game);
                return "success";
            }
        }

        return "此游戏已经发布，无需再次创建";
    }
    //修改游戏相关信息，包括游戏描述、开发者、发行商、发布日期、平台信息等
    public String updateInfo(Game game){
        game.setDescription(game.getDescription());
        game.setDeveloper(game.getDeveloper());
        game.setPublisher(game.getPublisher());
        game.setReleasedate(game.getReleasedate());
        game.setPlatforms(game.getPlatforms());
        int update=gameMapper.update(game,new QueryWrapper<Game>().eq("title",game.getTitle()));
        if(update>0){
            return "success";
        }
        return "更新游戏信息失败";
    }
}
