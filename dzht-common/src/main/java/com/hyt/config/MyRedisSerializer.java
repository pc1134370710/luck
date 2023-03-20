package com.hyt.config;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义 Redis 序列化
 */

public class MyRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public MyRedisSerializer(){
        this(StandardCharsets.UTF_8);
    }
    public MyRedisSerializer(Charset charset){
        Assert.notNull(charset,"charset 不能为空");
        this.charset=charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null){
            return new byte[0];
        }
        if(o instanceof String ){
            return o.toString().getBytes(charset);
        }else{
            String string = JSON.toJSONString(o);
            return string.getBytes(charset);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes,charset)) ;
    }
}
