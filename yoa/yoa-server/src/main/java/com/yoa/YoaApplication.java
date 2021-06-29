package com.yoa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.yoa.mapper")
@SpringBootApplication
//定时任务，用户重新发送rabbitmq失败的消息，暂未开启
//@EnableScheduling
public class YoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoaApplication.class, args);
    }

}
