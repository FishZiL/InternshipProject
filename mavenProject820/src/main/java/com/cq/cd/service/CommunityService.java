package com.cq.cd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.cd.pojo.Community;
import com.cq.cd.pojo.User;

public interface CommunityService extends IService<Community> {
    public String CreateCommunity(Community community, String name, String username  );
}
