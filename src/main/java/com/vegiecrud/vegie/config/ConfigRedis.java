package com.vegiecrud.vegie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.vegiecrud.vegie.repository")
public class ConfigRedis {
    @Value("${spring.datasource.redis.host}")
    private String redisHost;
    @Value("${spring.datasource.redis.port}")
    private Integer redisPort;
    @Value("${spring.datasource.redis.max.total}")
    private Integer redisMaxTotal;
    @Value("${spring.datasource.redis.max.idle}")
    private Integer redisMaxIdle;
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        // handle configmap (string only)
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);


        JedisConnectionFactory connection  = new JedisConnectionFactory(config);
        connection.getPoolConfig().setMaxIdle(redisMaxIdle);
        connection.getPoolConfig().setMaxTotal(redisMaxTotal);
        return connection;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
