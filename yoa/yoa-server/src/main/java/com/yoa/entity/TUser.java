package com.yoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author liulei
 * @since 2021-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class TUser{

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     *  管理人员信息id
     * */
    private String employee;

    /**
     * 用户头像
     * */
    private String pictureUrl;

    /**
     * 显示登陆用户的工号，不属于此表中的字段
     * */
    @TableField(exist = false)
    private String jobName;

    /**
     * 显示登陆用户的角入职时间，不属于此表中的字段
     * */
    @TableField(exist = false)
    private LocalDate entryTime;

    /**
     * 显示登陆用户的角色名称，不属于此表中的字段
     * */
    @TableField(exist = false)
    private String roleName;

    /**
     * 显示未读消息数量
     * */
    @TableField(exist = false)
    private Integer unReadCount;
}
