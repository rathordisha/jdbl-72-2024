package com.example.L12_minor_project_1.config;

import com.example.L12_minor_project_1.dto.VisitorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, VisitorDto> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, VisitorDto> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
