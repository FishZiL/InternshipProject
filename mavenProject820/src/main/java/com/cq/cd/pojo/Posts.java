package com.cq.cd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("post")
@Data
public class Posts {
    private int postid;
    private int communityid;
    private int authorid;
    private String title;
    private String content;
}

