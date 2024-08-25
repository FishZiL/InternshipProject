package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.pojo.Game;

public interface GameService extends IService<Game> {
    public String CreateGame(Game game);
    public String platformInfo();
}
