package com.goodjob.notification.client;

import com.goodjob.common.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    public ApiResponse<Map<String, Object>> getCandidate(String candidateId) {
        log.warn("Fallback: Get candidate for candidate ID: {}", candidateId);
        return ApiResponse.error("Candidate service is unavailable");
    }

    @Override
    public ApiResponse<Map<String, Object>> getCandidateNotificationPreferences(String candidateId) {
        log.warn("Fallback: Get notification preferences for candidate ID: {}", candidateId);
        
        // Return default notification preferences
        Map<String, Object> defaultPreferences = new HashMap<>();
        defaultPreferences.put("email", true);
        defaultPreferences.put("push", true);
        defaultPreferences.put("sms", false);
        defaultPreferences.put("inApp", true);
        
        return ApiResponse.success(defaultPreferences, 
                "Candidate service is unavailable, returning default preferences");
    }
} 