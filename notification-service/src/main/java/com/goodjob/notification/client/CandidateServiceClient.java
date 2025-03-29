package com.goodjob.notification.client;

import com.goodjob.common.config.FeignClientConfig;
import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.feign.BaseFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Feign client for the candidate service.
 * Provides methods to interact with the candidate service.
 */
@FeignClient(
    name = "candidate-service",
    configuration = FeignClientConfig.class,
    fallback = CandidateServiceClientFallback.class
)
public interface CandidateServiceClient extends BaseFeignClient {

    /**
     * Gets a candidate by ID.
     *
     * @param candidateId the candidate ID
     * @return the candidate details
     */
    @GetMapping("/api/candidates/{candidateId}")
    ApiResponse<Map<String, Object>> getCandidate(@PathVariable String candidateId);

    /**
     * Gets a candidate's notification preferences.
     *
     * @param candidateId the candidate ID
     * @return the notification preferences
     */
    @GetMapping("/api/candidates/{candidateId}/notification-preferences")
    ApiResponse<Map<String, Object>> getCandidateNotificationPreferences(@PathVariable String candidateId);
} 