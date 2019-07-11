package com.luo.controller;

import com.luo.shiro.MyShiroDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private MyShiroDemo myShiroDemo;

    @GetMapping("login")
    public Map<String,Object> login(String userName, String password) {
        return myShiroDemo.login(userName, password);
    }
}