package com.yoa.timedTask;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.BlockedCallback;
import com.yoa.entity.TEmployee;
import com.yoa.entity.TRabbitmqLog;
import com.yoa.service.ITEmployeeService;
import com.yoa.service.ITRabbitmqLogService;
import com.yoa.uitl.RabbitConstant;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 * */
@Configuration
public class MailTask {

    @Autowired
    ITRabbitmqLogService itRabbitmqLogService;

    @Autowired
    ITEmployeeService itEmployeeService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    //定时任务发送，10秒1次
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        /**
         * 查询t_Rabbitmq表中所有状态为0(未成功)的数据，
         * 和重试时间小于当前时间的数据(当失败后超过一分钟时开始重试)
         * */
        List<TRabbitmqLog> tRabbitmqLogs = itRabbitmqLogService.list(new QueryWrapper<TRabbitmqLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));

        tRabbitmqLogs.forEach(tRabbitmqLog -> {
            //如果重试次数大于等于3，直接定义为失败
            if (3<=tRabbitmqLog.getCount()){
                itRabbitmqLogService.update(new UpdateWrapper<TRabbitmqLog>().set("status",2).eq("obj_id",tRabbitmqLog.getObjId()));
            }
            //否则将重试结果加1，更新时间设定为当前时间，重试时间加1分钟
            itRabbitmqLogService.update(new UpdateWrapper<TRabbitmqLog>()
                    .set("count",tRabbitmqLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now().plusMinutes(RabbitConstant.MSG_TIMEOUT)));

            //并且重新发送
            TEmployee tEmployee = itEmployeeService.getMyData(tRabbitmqLog.getEmpid());
            rabbitTemplate.convertAndSend(RabbitConstant.MAIL_EXCHANGE_NAME,RabbitConstant.MAIL_ROUTING_KEY_NAME,tEmployee,new CorrelationData(tRabbitmqLog.getObjId()));
        });
    }
}
