package com.yoa.service;


import com.yoa.entity.TUser;
import com.yoa.entity.TuserLogin;
import com.yoa.uitl.JwtUitl;
import com.yoa.uitl.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class LoginService {


    @Autowired
    HttpServletRequest request;
    @Autowired
    RedisTemplate redisTemplate;

    public Result login(TuserLogin tuserLogin) {
        //使用shiro获取当前用户实体
        Subject subject = SecurityUtils.getSubject();
        //封装用户提交的数据
        UsernamePasswordToken token = new UsernamePasswordToken(tuserLogin.getUsername(), tuserLogin.getPassword());
        try {
//            log.info("上传的code：" + tuserLogin.getCode());
//            log.info("保存到code：" + redisTemplate.opsForValue().get("code"));
            boolean redisCode = redisTemplate.hasKey(tuserLogin.getCode());
            if (redisCode){
                if (tuserLogin.getCode().equals(redisTemplate.opsForValue().get(tuserLogin.getCode()))) {
                    //使用shiro认证
                    subject.login(token);
                    //map用于设置token的值
                    HashMap<String, String> stringHashMap = new HashMap<>();
                    //取出shiroRealm认证返回值时候存储的TUser对象
                    TUser tUser = (TUser) SecurityUtils.getSubject().getPrincipal();

                    stringHashMap.put("username", tUser.getUsername());
                    stringHashMap.put("userid", tUser.getId());
                    stringHashMap.put("pictureUrl",tUser.getPictureUrl());
                    //向token中传值
                    String JWToken = JwtUitl.setToken(stringHashMap);
                    HashMap<String, String> ResultMap = new HashMap<>();
                    ResultMap.put("token", JWToken);
                    return Result.succ("登陆成功", ResultMap);
                } else {
                    return Result.fail(400, "验证码错误");
                }
            }else {
                return Result.fail(400,"验证码已过期");
            }
        } catch (Exception e) {
            if (e instanceof UnknownAccountException) {
                log.info("Exception caught:" + e.getMessage());
                return Result.fail(400, e.getMessage());
            } else {
                log.info("Exception caught:" + e.getMessage());
                return Result.fail(400, "密码错误");
            }
        } finally {
            redisTemplate.delete(tuserLogin.getCode());
        }
    }
}