package com.lp.lpsystem.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lp.lpsystem.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
