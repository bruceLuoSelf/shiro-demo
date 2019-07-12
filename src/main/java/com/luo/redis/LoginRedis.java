package com.luo.redis;

import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author ljn
 * @date 2019/7/12.
 */
public class LoginRedis extends RedisManager {

    @Autowired
    StringRedisTemplate stringRedisTemplate;



}
