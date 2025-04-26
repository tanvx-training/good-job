package com.goodjob.common.api.feign.fallback;

import com.goodjob.common.api.feign.dto.job.JobDTO;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Fallback implementation for JobClient.
 * This class provides fallback methods for JobClient in case of service failure.
 */
@Slf4j
@Component
public class JobClientFallback implements JobClient {

    @Override
    public ApiResponse<JobDTO> getJobById(Long id) {
        log.error("Fallback: Unable to get job with ID {}", id);
        return ApiResponse.error("Job service is not available");
    }

    @Override
    public ApiResponse<PageResponseDTO<JobDTO>> getAllJobs(Integer page, Integer size, String sort) {
        log.error("Fallback: Unable to get jobs list (page={}, size={})", page, size);
        
        PageResponseDTO<JobDTO> emptyPage = new PageResponseDTO<>(
            Collections.emptyList(),
            0,
            0,
            page,
            size,
            true,
            true,
            true
        );
        
        return ApiResponse.error("Job service is not available", emptyPage);
    }
} 