package com.cq.cd.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import java.sql.Date;

@Data
public class Game {
    private int gameid;
    private String title;
    private String description;
    private String developer;
    private String publisher;
    private String releasedate;
    @TableField(value = "platforms",typeHandler = FastjsonTypeHandler.class)
    private Object platforms;
}
