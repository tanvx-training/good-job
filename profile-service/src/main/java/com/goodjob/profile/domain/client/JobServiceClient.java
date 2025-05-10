package com.goodjob.profile.domain.client;

import com.goodjob.profile.domain.profile.dto.JobRecommendationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign client for communication with the Job Service.
 */
@FeignClient(name = "job-service")
public interface JobServiceClient {

    @GetMapping("/api/v1/jobs/recommendations")
    List<JobRecommendationDTO> getJobRecommendations(@RequestParam("skills") List<String> skills);
} 