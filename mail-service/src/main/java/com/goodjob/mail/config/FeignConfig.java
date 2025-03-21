package com.goodjob.mail.config;

import com.goodjob.common.config.FeignClientConfig;
import com.goodjob.common.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for Feign clients in the mail service.
 * Enables and configures Feign clients for inter-service communication.
 */
@Configuration
@EnableFeignClients(basePackages = {"com.goodjob.common.feign", "com.goodjob.mail.feign"})
@Import({OpenFeignConfig.class, FeignClientConfig.class})
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