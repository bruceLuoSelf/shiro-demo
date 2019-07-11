package com.luo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@Component
public class MyShiroDemo {

    public Map<String, Object> login(String userName, String password) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            //4、登录，即身份验证
            subject.login(token);
            resultMap.put("code", "200");
            resultMap.put("msg", "登陆成功");

            //校验角色
            if (subject.hasRole("role1")){
                resultMap.put("roleMsg1","用户拥有角色1");
            }else {
                resultMap.put("roleMsg1","用户未拥有角色1");
            }

            if (subject.hasAllRoles(Arrays.asList("role1","role2"))){
                resultMap.put("roleMsg2","用户同时拥有角色1和角色2");
            }else {
                resultMap.put("roleMsg2","用户未同时拥有角色1和角色2");
            }

            //这里就不返回第三种方法了,直接打印了
            System.out.println(Arrays.asList(subject.hasRoles(Arrays.asList("role1","role2"))));

            //校验权限
            if (subject.isPermitted("permission1")){
                resultMap.put("PermittedMsg1","用户拥有权限1");
            }else {
                resultMap.put("PermittedMsg1","用户未拥有权限1");

            }
            if (subject.isPermittedAll("permission2","permission3")){
                resultMap.put("PermittedMsg2","用户同时拥有权限1和权限2");
            }else {
                resultMap.put("PermittedMsg2","用户未同时拥有权限1和权限2");
            }

        } catch (AuthenticationException e) {
            //5、身份验证失败
            resultMap.put("code", "-1");
            resultMap.put("msg", "登陆失败");
        }
        //6、退出
        subject.logout();
        return resultMap;
    }
}
