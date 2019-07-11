package com.luo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author ljn
 * @date 2019/7/11.
 * 自定义realm类实现，业务逻辑
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        //1.获取用户输入的账号
//        String username = (String)token.getPrincipal();
//        //2.通过username从数据库中查找到user实体
//        User user = getUserByUserName(username);
//        if(user == null){
//            return null;
//        }
//        //3.通过SimpleAuthenticationInfo做身份处理
//        SimpleAuthenticationInfo simpleAuthenticationInfo =
//                new SimpleAuthenticationInfo(user,user.getPassword(),getName());
//        //4.用户账号状态验证等其他业务操作
//        if(!user.getAvailable()){
//            throw new AuthenticationException("该账号已经被禁用");
//        }
//        //5.返回身份处理对象
//        return simpleAuthenticationInfo;
        return null;
    }
}
