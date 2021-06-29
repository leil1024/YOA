package com.yoa.configuration;

import com.yoa.interceptor.JWTinterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器注册登记类
 * */
@Configuration
public class InterecptorConfig implements WebMvcConfigurer {

    @Autowired
    private JWTinterceptor jwTinterceptor;


    /**
     * 添加JWT拦截器
     * 添加需要token验证的请求
     * */
    //----------------------------暂未配置拦截请求----------------------------
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwTinterceptor)
                //要拦截的请求
                .addPathPatterns("/text")
                //不拦截的请求
                .excludePathPatterns("/login");
    }
}
