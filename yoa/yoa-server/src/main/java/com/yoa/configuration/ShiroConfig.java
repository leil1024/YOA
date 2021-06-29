package com.yoa.configuration;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * shiro安全框架配置：
 * */
@Configuration
public class ShiroConfig {

    /**
     * realm安全数据源
     * */
    @Bean
    public ShiroRealm realm(){
        return new ShiroRealm();
    }


    /**
     *安全管理器
     * */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //配置realm安全数据源
        defaultWebSecurityManager.setRealm(realm());
        return defaultWebSecurityManager;
    }

    /**
     * shiro请求拦截器
     * shiroFilterFactoryBea会拦截客户端所有请求，交给安全管理器
     * 安全管理器通过realm进行数据验证
     * */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        /**
         * 在拦截器中配置请求拦截，当vue请求后端接口时，根据用户权限进行判断
         * 如果没有权限，则返回一个公共返回对象Result类，code=403
         * vue解析到403时，则在客户端进行提示权限不足
         * */
//        HashMap<String, String> filterMap = new HashMap<>();
//        filterMap.put("/login","authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }
}
