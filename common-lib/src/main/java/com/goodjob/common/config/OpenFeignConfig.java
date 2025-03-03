//package com.goodjob.common.config;
//
//import feign.RequestInterceptor;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
///**
// * Configuration for enabling OpenFeign in services.
// * Import this configuration to enable Feign clients in a service.
// */
//@Configuration
//@EnableFeignClients(basePackages = "com.goodjob")
//@Import({FeignClientConfig.class, CircuitBreakerConfig.class})
//public class OpenFeignConfig {
//
//    /**
//     * Creates a Feign authentication interceptor.
//     *
//     * @return the authentication interceptor
//     */
//    @Bean
//    public RequestInterceptor feignAuthInterceptor() {
//        return new FeignAuthInterceptor();
//    }
//}