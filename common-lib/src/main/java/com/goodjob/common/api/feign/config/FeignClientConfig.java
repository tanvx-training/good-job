package com.goodjob.common.api.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Objects;

/**
 * Base configuration for all Feign clients.
 * This configuration will be applied to all Feign clients defined in common-lib.
 */
@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public HttpMessageConverters httpMessageConverters(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper); // Link the ObjectMapper to the converter
        return new HttpMessageConverters(converter);
    }

    /**
     * Configure logging level for Feign clients.
     * @return Logger.Level Full logging level for development, can be adjusted for production
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Request interceptor to add common headers to all Feign requests.
     * @return RequestInterceptor that adds common headers
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return (RequestTemplate requestTemplate) -> {
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Content-Type", "application/json");
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                HttpServletRequest request = attributes.getRequest();
                String username = request.getHeader("X-Auth-Username");
                String roles = request.getHeader("X-Auth-Roles");
                String permissions = request.getHeader("X-Auth-Permissions");

                if (Objects.nonNull(username)) {
                    requestTemplate.header("X-Auth-Username", username);
                }
                if (Objects.nonNull(roles)) {
                    requestTemplate.header("X-Auth-Roles", roles);
                }
                if (Objects.nonNull(permissions)) {
                    requestTemplate.header("X-Auth-Permissions", permissions);
                }
            }
        };
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> httpMessageConverters(objectMapper)));
    }

    /**
     * Error decoder to convert HTTP errors to custom exceptions.
     * @return ErrorDecoder for handling HTTP errors
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
} 