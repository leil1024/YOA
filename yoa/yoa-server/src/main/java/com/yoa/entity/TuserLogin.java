package com.yoa.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于登陆的user实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuserLogin {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     * */
    private String code;
}
