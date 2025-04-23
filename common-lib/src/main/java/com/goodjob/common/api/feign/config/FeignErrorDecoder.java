package com.goodjob.common.api.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.common.application.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

/**
 * Custom error decoder for Feign clients.
 * Converts HTTP errors to custom exceptions based on response status and body.
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            ApiResponse<?> apiResponse = objectMapper.readValue(bodyIs, ApiResponse.class);
            
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
            
            if (responseStatus.is4xxClientError()) {
                if (responseStatus == HttpStatus.NOT_FOUND) {
                    return new ResourceNotFoundException(apiResponse.getMessage());
                }
                return new IllegalArgumentException(apiResponse.getMessage());
            } else if (responseStatus.is5xxServerError()) {
                return new ServiceException(apiResponse.getMessage());
            }
            
            log.error("Feign client error on {}: {} - {}", methodKey, response.status(), apiResponse.getMessage());
            return new ServiceException("Error calling service: " + apiResponse.getMessage());
            
        } catch (IOException e) {
            log.error("Error parsing error response for {}", methodKey, e);
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }
} 