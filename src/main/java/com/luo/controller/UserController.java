package com.luo.controller;

import com.alibaba.fastjson.JSONObject;
import com.luo.dao.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("login")
    public Map<String,Object> login(String userName, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            response.sendRedirect("/success.html");
            return resultMap;
        }else{
            token.clear();
            return resultMap;
        }
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("getUserInfo")
    public String getCurrentUser() {
        return JSONObject.toJSONString(currentUser());
    }

    /**
     * 退出登陆
     * @return
     */
    @GetMapping("logout")
    public Map<String,Object> logout() {
        Map<String,Object> resultMap = new HashMap<>();
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            resultMap.put("msg" , "退出成功");
        } catch (Exception e) {
            resultMap.put("msg" , e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/sing")
    @RequiresRoles("主管")
    public String sing(){
        return "sing";
    }

    @GetMapping("/jump")
    @RequiresPermissions("jump")
    public String jump(){
        return "jump";
    }


    @GetMapping("/dog")
    public String dog(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("主管")){
            return "主管√";
        }
        else {
            return  "主管×";
        }
    }

    @GetMapping("/cat")
    public String cat(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("主管")){
            return "主管√";
        }
        else {
            return  "主管×";
        }
    }
    @GetMapping("/rap")
    public String rap() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("rap")) {
            return "rap";
        } else {
            return "没权限你Rap个锤子啊!";
        }
    }


}
