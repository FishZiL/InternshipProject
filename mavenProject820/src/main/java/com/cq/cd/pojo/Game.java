package com.cq.cd.pojo;
import lombok.Data;


@Data
public class Game {
    private int gameid;
    private String title;
    private String gameurl;
    private String description;
    private String developer;
    private String publisher;
    private String releasedate;
}
