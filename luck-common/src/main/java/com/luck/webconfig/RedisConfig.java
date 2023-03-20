package com.luck.webconfig;

import com.alibaba.fastjson.serializer.AdderSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
            RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
        MyRedisSerializer myRedisSerializer = new MyRedisSerializer();
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        ObjectSerializer objectSerializer = new AdderSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(myRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(myRedisSerializer);
        return redisTemplate;
    }

}
