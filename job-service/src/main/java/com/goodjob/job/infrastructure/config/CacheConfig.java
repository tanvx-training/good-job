package com.goodjob.job.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class CacheConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.cache.redis.time-to-live}")
    private long defaultTTL;

    private final ObjectMapper objectMapper;

    public CacheConfig(@Qualifier("redisObjectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);
        factory.afterPropertiesSet(); // Khởi tạo kết nối
        log.info("Connected to Redis at {}:{}", redisHost, redisPort);
        return factory;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        RedisCacheConfiguration defaultCacheConfig = createCacheConfiguration(Duration.ofSeconds(defaultTTL));
        // Cấu hình cho từng loại cache với TTL khác nhau
        // Dữ liệu metadata (ít thay đổi) - cache lâu hơn
        cacheConfigurations.put("benefits", createCacheConfiguration(Duration.ofHours(24)));
        cacheConfigurations.put("skills", createCacheConfiguration(Duration.ofHours(24)));
        cacheConfigurations.put("industries", createCacheConfiguration(Duration.ofHours(24)));
        // Dữ liệu company (thay đổi thường xuyên hơn) - cache ngắn hơn
        cacheConfigurations.put("companies", createCacheConfiguration(Duration.ofHours(6)));
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    private RedisCacheConfiguration createCacheConfiguration(Duration ttl) {
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        return RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .entryTtl(ttl)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .disableCachingNullValues()
                .prefixCacheNameWith("job-service:")
                .computePrefixWith(cacheName -> "cache:" + cacheName + ":");
    }
}
