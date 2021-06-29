package com.yoa.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *    rabbitmq用于消息确认机制的数据库的实体类
 *    生产者发送消息，消费者接受消息，改变数据库中的消息接收状态，确认消费者已经接收到消息
 * </p>
 *
 * @author liulei
 * @since 2021-06-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TRabbitmqLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private String objId;

    /**
     * 员工ID
     */
    private String empid;

    /**
     * 状态(0投递中，1投递成功，2投递失败)
     */
    private Integer status;

    /**
     * 路由键
     */
    @TableField("routeKey")
    private String routekey;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 重试次数
     */
    private Integer count;

    /**
     * 重试时间
     */
    @TableField("tryTime")
    private LocalDateTime trytime;

    /**
     * 创建时间
     */
    @TableField("createTimt")
    private LocalDateTime createtimt;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updatetime;


}
