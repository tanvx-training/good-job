package com.goodjob.mail.feign;

import com.goodjob.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Fallback implementation for the candidate service client.
 * Provides fallback behavior when the candidate service is unavailable.
 */
@Component
@Slf4j
public class CandidateServiceClientFallback implements CandidateServiceClient {

    @Override
    public ApiResponse<Object> health() {
        log.warn("Fallback: Health check for candidate service");
        return ApiResponse.error("Candidate service is unavailable");
    }

    @Override
    public ApiResponse<Object> info() {
        log.warn("Fallback: Info for candidate service");
        return ApiResponse.error("Candidate service is unavailable");
    }

    @Override
    public ApiResponse<Object> metrics(String name) {
        log.warn("Fallback: Metrics for candidate service");
        return ApiResponse.error("Candidate service is unavailable");
    }

    @Override
    public ApiResponse<Map<String, Object>> getCandidateById(Long id) {
        log.warn("Fallback: Get candidate by ID: {}", id);
        return ApiResponse.error("Candidate service is unavailable");
    }

    @Override
    public ApiResponse<String> getCandidateEmail(Long id) {
        log.warn("Fallback: Get candidate email by ID: {}", id);
        return ApiResponse.error("Candidate service is unavailable");
    }
} 