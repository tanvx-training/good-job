package com.goodjob.mail.feign;

import com.goodjob.common.config.FeignClientConfig;
import com.goodjob.common.dto.ApiResponse;
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
     * @param id the candidate ID
     * @return the candidate data
     */
    @GetMapping("/api/candidates/{id}")
    ApiResponse<Map<String, Object>> getCandidateById(@PathVariable Long id);

    /**
     * Gets a candidate's email by ID.
     *
     * @param id the candidate ID
     * @return the candidate's email
     */
    @GetMapping("/api/candidates/{id}/email")
    ApiResponse<String> getCandidateEmail(@PathVariable Long id);
} 