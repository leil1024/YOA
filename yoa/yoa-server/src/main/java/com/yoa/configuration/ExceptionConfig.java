package com.yoa.configuration;

import com.yoa.uitl.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 * */
@Slf4j
@RestControllerAdvice       //springboot全局异常处理
public class ExceptionConfig {


    //统一处理某一类异常
    @ExceptionHandler(value = RuntimeException.class)
    //抛出异常状态码
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result getRuntime(RuntimeException runtimeException){
        log.info("----------捕获到异常：" + runtimeException);
        return Result.fail(400,runtimeException.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result getNullPointerException(AuthenticationException authenticationException){
        log.info("----------捕获到异常：" + authenticationException);
        return Result.fail(400,authenticationException.getMessage());
    }

}
