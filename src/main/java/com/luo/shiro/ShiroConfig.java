package com.luo.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ljn
 * @date 2019/7/11.
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean webFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String,String> filterChainMap = new LinkedHashMap<String,String>(16);

        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        //配置退出过滤器logout，由shiro实现
        filterChainMap.put("user/logout","logout");
        filterChainMap.put("/login.html","anon");
        filterChainMap.put("/user/login","anon");
        filterChainMap.put("/**", "authc");

        //设置拦截请求后跳转的URL.
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
        securityManager.setRealm(customRealm());
        return securityManager;
    }

}
