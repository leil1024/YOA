package com.yoa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger配置类
 * 配置swagger主页的基本信息
 * http://localhost:13370/swagger-ui/index.html
 * */

@EnableOpenApi
@Configuration
@ComponentScan("com.yoa.controller")
public class SwaggerConfigurtion{

    @Bean
    public Docket docket(){
        //指定API类型为swagger2
        //用于定义API的文档信息
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描该包下的接口
                .apis(RequestHandlerSelectors.basePackage("com.yoa.controller"))
                .build();
    }

    public ApiInfo apiInfo(){
        //联系人信息
        Contact DEFAULT_CONTACT  = new Contact("刘磊","http://www.leil.com","leil1024@163.com");

        return new ApiInfoBuilder()
                //标题
                .title("云OA系统接口API")
                //联系人信息
                .contact(DEFAULT_CONTACT)
                //详细信息
                .description("用于云OA系统开发中的api接口测试")
                //版本号
                .version("1.0")
                //网站地址
                .termsOfServiceUrl("http://www.zanwu.com")
                .build();
    }

}