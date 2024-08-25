package com.cq.cd.pojo;

import lombok.Data;
import java.sql.Date;

@Data
public class Community {
    private int communityid;
    private String name;
    private String description;
    private String guidelines;
    private int categoryid;
    private String gameid;
    private int createdby;
}
