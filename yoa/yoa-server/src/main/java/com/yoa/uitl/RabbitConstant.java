package com.yoa.uitl;

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

/**
 * rabbitmq常量
 * 对用rabbitmq类中的字段
 * */

public class RabbitConstant {

    /**
     * 消息状态：未投递
     * */
    public static final Integer DELIVERiING = 0;

    /**消息状态：投递成功*/
    public static final Integer SUCCESS = 1;

    /**消息状态：投递失败*/
    public static final Integer FAILURE = 2;

    /**最大重试次数*/
    public static final Integer MAX_TRY_COUNT = 3;

    /**消息超时时间（分钟）*/
    public static final Integer MSG_TIMEOUT = 1;

    /**消息队列名称*/
    public static final String MAIL_QUEUE_NAME = "mail.welcome";

    /**
     * 交换机
     * 服务器会根据路由键将消息从交换器路由到队列上去
     * */
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";

    /**路由键*/
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
}
