package com.luo.shiro;

import com.luo.dao.entity.Permission;
import com.luo.dao.entity.Role;
import com.luo.dao.entity.User;
import com.luo.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ljn
 * @date 2019/7/11.
 * 自定义realm类实现，业务逻辑
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户输入的账号
        String username = (String)token.getPrincipal();
        //2.通过username从数据库中查找到user实体
        User user = userService.getUser(username);
        if(user == null){
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getPasswordSalt()),getName());
        //4.返回身份处理对象
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("-------------认证用户---------------");
        //获取当前登录的用户
        User user = (User) principal.getPrimaryPrincipal();
        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = user.getRoleList();
        if (CollectionUtils.isEmpty(roleList)) {
            return authorizationInfo;
        }
        //添加角色
        for (Role role : roleList) {
            authorizationInfo.addRole(role.getRoleName());
            List<Permission> permissionList = role.getPermissionList();
            if (CollectionUtils.isEmpty(permissionList)) {
                continue;
            }
            //添加权限
            for (Permission permission : permissionList) {
                authorizationInfo.addStringPermission(permission.getPermissionName());
            }
        }
        return authorizationInfo;
    }




}
