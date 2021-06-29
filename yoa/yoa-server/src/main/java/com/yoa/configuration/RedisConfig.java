package com.yoa.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis配置类
 * 解决Redis存储乱码问题
 * */


@Configuration
public class RedisConfig {

        @Bean
        public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
            RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory);

            // 使用Jackson2JsonRedisSerialize替换默认序列化
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

            // 设置key和value的序列化规则
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
        }

//        @Bean
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
//            RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//            //Sring类型key序列器
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
//            //Strign类型value序列器
//            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//            //Hash类型key序列器
//            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//            //Hash类型value序列器
//            redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//            redisTemplate.setConnectionFactory(connectionFactory);
//            return redisTemplate;
//        }
}
