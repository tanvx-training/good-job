package com.goodjob.common.config;

import com.goodjob.common.exception.BadRequestException;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.common.exception.ServiceException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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