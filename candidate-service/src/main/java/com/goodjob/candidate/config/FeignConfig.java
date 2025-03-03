package com.goodjob.candidate.config;

import com.goodjob.common.config.FeignAuthInterceptor;
import com.goodjob.common.config.FeignClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for Feign clients in the candidate service.
 * Imports common Feign configuration and adds service-specific configuration.
 */
@Configuration
@Import(FeignClientConfig.class)
public class FeignConfig {

    /**
     * Creates a Feign authentication interceptor.
     *
     * @return the authentication interceptor
     */
    @Bean
    public FeignAuthInterceptor feignAuthInterceptor() {
        return new FeignAuthInterceptor();
    }
} 