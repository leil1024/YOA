package com.yoa.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yoa.entity.TRabbitmqLog;
import com.yoa.service.ITRabbitmqLogService;
import com.yoa.uitl.RabbitConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ配置类，用于绑定消息队列，交换机和路由键
 * */

@Configuration
public class RabbitMQConfig {

    //在日志输出的时候，可以打印出日志所属的类
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    ITRabbitmqLogService itRabbitmqLogService;


    @Bean
    //RabbitTemplate用于收发消息，CachingConnectionFactory作为连接工程
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 消息确认回调
         * data：消息唯一标识
         * ack：确认结果
         * cause：失败原因
         *
         * 添加员工之后发送消息，携带插入消息的主键ID，如果消息结果被正确接收，打印日志，将根据ID将数据库中的状态字段改为1
         * */
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if (ack){
                LOGGER.info("消息发送成功===============>",data.getId());
                itRabbitmqLogService.update(new UpdateWrapper<TRabbitmqLog>().set("status",1).eq("obj_id",data.getId()));
            }else {
                LOGGER.error("消息发送失败================>",data.getId());
            }
        });

        /**
         * 消息失败回调
         * ReturnsCallback:消息失败回调，包含路由器，消息结果，消息状态，失败信息等
         * */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                LOGGER.error("消息丢失================>" + returnedMessage.getMessage());
            }
        });
        return rabbitTemplate;
    }



    /**声明一个消息队列*/
    @Bean
    public Queue queue(){
        return new Queue(RabbitConstant.MAIL_QUEUE_NAME);
    }

    /**声明一个交换机*/
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitConstant.MAIL_EXCHANGE_NAME);
    }

    /**使用路由键将交换机和消息队列绑定*/
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitConstant.MAIL_ROUTING_KEY_NAME);
    }
}
