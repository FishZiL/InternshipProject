package com.cq.cd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cq.cd.pojo.Posts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper extends BaseMapper<Posts> {
}
