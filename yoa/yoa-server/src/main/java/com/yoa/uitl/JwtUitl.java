/**
 * JWT工具类
 * */



package com.yoa.uitl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class JwtUitl {

    private static final String SING = "HVUB&*^/66ad";

    //生成token
    public static String setToken(Map<String,String> map){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,1);

        JWTCreator.Builder builder = JWT.create();
        map.forEach((key,value)->{
            builder.withClaim(key,value);
        });
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
        return token;
    }

    //获取token
    public static DecodedJWT DecryptToken(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}
