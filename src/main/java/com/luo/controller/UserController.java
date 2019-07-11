package com.luo.controller;

import com.luo.dao.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * shiro获取当前用户
     * @return
     */
    private User currentUser(){
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        return  currentUser;
    }

    @GetMapping("login")
    public Map<String,Object> login(String userName, String password) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //主体提交登录请求到SecurityManager
            subject.login(token);
        }catch (IncorrectCredentialsException ice){
            resultMap.put("msg","密码不正确");
        }catch(UnknownAccountException uae){
            resultMap.put("msg","账号不存在");
        }catch(AuthenticationException ae){
            resultMap.put("msg","状态不正常");
        }
        if(subject.isAuthenticated()){
            resultMap.put("currentUser",currentUser());
            return resultMap;
        }else{
            token.clear();
            return resultMap;
        }
    }
}
