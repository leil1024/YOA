package com.yoa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * websocket消息类
 * */
@Data
//此注解会生成equals(Object other) 和 hashCode()方法
@EqualsAndHashCode
//@Accessors用于配置getter和setter方法的生成结果
@Accessors
public class TChat {

    //发送人
    private String from;
    //接收人
    private String to;
    //消息内容
    private String context;
    //消息时间
    private LocalDateTime date;
}
