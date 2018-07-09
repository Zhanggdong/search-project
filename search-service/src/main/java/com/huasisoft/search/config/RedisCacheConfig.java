package com.huasisoft.search.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;


/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 15:54
 * @Description Redis 集成配置类
 * @Version 2.0.0
 */
@Configuration
@ComponentScan(basePackages = {"com.huasisoft.search.demo","com.huasisoft.search.core","com.huasisoft.search.admin"})
@EnableRedisRepositories(basePackages = {"com.huasisoft.search.demo","com.huasisoft.search.core","com.huasisoft.search.admin"})
@PropertySource(value = {"classpath:/properties/redis.properties"})
public class RedisCacheConfig extends CachingConfigurerSupport{
    @Value("${redis.host}")
    String host;
    @Value("${redis.port}")
    String port;
    @Value("${redis.password}")
    String password;
    @Value("${redis.maxIdle}")
    String maxIdle;
    @Value("${redis.minIdle}")
    String minIdle;
    @Value("${redis.maxTotal}")
    String maxTotal;
    @Value("${redis.maxWaitMillis}")
    String maxWaitMillis;
    @Value("${redis.blockWhenExhausted}")
    String blockWhenExhausted;
    @Value("${redis.minEvictableIdleTimeMillis}")
    String  minEvictableIdleTimeMillis;
    @Value("${redis.numTestsPerEvictionRun}")
    String numTestsPerEvictionRun;
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    String  timeBetweenEvictionRunsMillis;
    @Value("${redis.testOnBorrow}")
    String testOnBorrow;
    @Value("${redis.testOnReturn}")
    String testOnReturn;
    @Value("${redis.timeout}")
    String timeout;
    @Value("${defaultCacheExpireTime}")
    String defaultCacheExpireTime;


    @Bean
    JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.valueOf(maxTotal));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(maxIdle));
        jedisPoolConfig.setMinIdle(Integer.valueOf(minIdle));
        jedisPoolConfig.setMaxWaitMillis(Long.valueOf(maxWaitMillis));
        jedisPoolConfig.setBlockWhenExhausted(Boolean.valueOf(blockWhenExhausted));
        jedisPoolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
        jedisPoolConfig.setNumTestsPerEvictionRun(Integer.valueOf(numTestsPerEvictionRun));
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        jedisPoolConfig.setTestOnReturn(Boolean.valueOf(testOnReturn));
        return jedisPoolConfig;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(Integer.valueOf(port));
        jedisConnectionFactory.setTimeout(Integer.valueOf(timeout));
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        jackson2JsonRedisSerializer(redisTemplate);
        //stringSerializer(redisTemplate);
        return redisTemplate;
    }

    private void stringSerializer(RedisTemplate<String, Object> redisTemplate){
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer );
        redisTemplate.setValueSerializer(stringSerializer );
        redisTemplate.setHashKeySerializer(stringSerializer );
        redisTemplate.setHashValueSerializer(stringSerializer );
    }

    /**
     * 设置数据存入 redis 的序列化方式
     *
     * @param redisTemplate
     */
    private void jackson2JsonRedisSerializer(RedisTemplate<String, Object> redisTemplate) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
    }


    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

}
