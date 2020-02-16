package com.zz.lab.cache.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                   MessageListener messageListener,
                                                   MessageListener messageListener2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener, new ChannelTopic("topic"));
        container.addMessageListener(messageListener2, new PatternTopic("topic*"));
        return container;
    }

    @Bean
    public MessageListener messageListener() {
        RedisSerializer<String> redisSerializer =  new StringRedisSerializer();
        return (x,y) -> System.out.println("listener1 get message " + redisSerializer.deserialize(y) + " on " + new String(y));
    }

    @Bean
    public MessageListener messageListener2() {
        RedisSerializer<String> redisSerializer =  new StringRedisSerializer();
        return (x,y) -> System.out.println("listener2 get message " + redisSerializer.deserialize(y) + " on " + new String(y));
    }
}
