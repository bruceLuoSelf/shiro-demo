package com.luo.dao.mapper;

import com.luo.dao.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    User selectByUserName(String userName);
}