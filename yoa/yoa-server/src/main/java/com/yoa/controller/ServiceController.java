package com.yoa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yoa.entity.TuserLogin;
import com.yoa.service.LoginService;
import com.yoa.uitl.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ServiceController {

    @Autowired
    LoginService loginService;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @CrossOrigin
    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "shiro登陆,根据用户名和密码")
    public Result login(TuserLogin tuserLogin){
        return loginService.login(tuserLogin);
    }


    @GetMapping("/text")
    @ApiOperation(value = "测试请求",notes = "需要携带token才能访问")
    public Result text(){
        return Result.succ("请求成功");
    }



}
