package com.luo.service;

import com.luo.dao.entity.User;
import com.luo.dao.mapper.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@Service
public class UserService {

    @Value("${user.salt}")
    private String salt;

    @Autowired
    private UserMapper userMapper;

    public User getUser(String userName) {
        return userMapper.selectByUserName(userName);
    }

    /**
     * 注册账号
     * @param userName
     * @param password
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(String userName, String password) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            throw new RuntimeException("参数异常");
        }
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userName);
        int count = userMapper.selectCountByExample(example);
        if (count > 0) {
            throw new RuntimeException("该账号已被注册");
        }
        //第一个参数：明文密码；第二个：盐；第三个：散列次数
        Md5Hash md5Hash = new Md5Hash(password, salt, 1);
        password = md5Hash.toString();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setPasswordSalt(salt);
        userMapper.insert(user);
    }
}
