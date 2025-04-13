package com.goodjob.posting.feign;

import com.goodjob.common.exception.BadRequestException;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.common.exception.ServiceException;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Configuration for Feign clients.
 * Provides common configuration for all Feign clients in the application.
 */
@Slf4j
public class FeignClientConfig {

    /**
     * Configures the logging level for Feign clients.
     *
     * @return the logging level
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Configures the error decoder for Feign clients.
     *
     * @return the error decoder
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return (RequestTemplate requestTemplate) -> {
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

    /**
     * Custom error decoder for Feign clients.
     * Translates HTTP error responses into appropriate exceptions.
     */
    @Slf4j
    public static class FeignErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultErrorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
            String requestUrl = response.request().url();
            String responseBody = getResponseBody(response);

            log.error("Error in Feign client call: {} {} - Status: {}, Body: {}", 
                    response.request().httpMethod(), requestUrl, responseStatus, responseBody);

            if (responseStatus.is4xxClientError()) {
                if (responseStatus == HttpStatus.NOT_FOUND) {
                    return new ResourceNotFoundException("Resource", "url", requestUrl);
                } else if (responseStatus == HttpStatus.BAD_REQUEST) {
                    return new BadRequestException("Bad request: " + responseBody);
                } else {
                    return new BadRequestException("Client error: " + responseBody);
                }
            } else if (responseStatus.is5xxServerError()) {
                return new ServiceException("Remote service error: " + responseBody);
            }

            return defaultErrorDecoder.decode(methodKey, response);
        }

        private String getResponseBody(Response response) {
            try {
                if (response.body() != null) {
                    try (InputStream bodyIs = response.body().asInputStream()) {
                        return new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);
                    }
                }
            } catch (IOException e) {
                log.error("Error reading response body", e);
            }
            return "[no body]";
        }
    }
} 