package com.luo.service;

import com.luo.dao.entity.User;
import com.luo.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUser(String userName) {
        return userMapper.selectByUserName(userName);
    }
}
