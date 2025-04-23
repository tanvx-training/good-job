package com.goodjob.common.api.feign.client;

import com.goodjob.common.api.feign.dto.job.JobDTO;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.api.feign.config.FeignClientConfig;
import com.goodjob.common.api.feign.fallback.JobClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for job-service.
 * This interface defines methods to call the job-service APIs.
 */
@FeignClient(
    name = "job-service",
    configuration = FeignClientConfig.class,
    fallback = JobClientFallback.class
)
public interface JobClient {

    /**
     * Get job by ID.
     *
     * @param id the job ID
     * @return ApiResponse containing the job details
     */
    @GetMapping("/api/v1/jobs/{id}")
    ApiResponse<JobDTO> getJobById(@PathVariable("id") Long id);

    /**
     * Get all jobs with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @param sort the sort criteria (format: property,direction - e.g., "jobId,asc")
     * @return ApiResponse containing paginated job list
     */
    @GetMapping("/api/v1/jobs")
    ApiResponse<PageResponseDTO<JobDTO>> getAllJobs(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "20") Integer size,
        @RequestParam(value = "sort", defaultValue = "jobId,asc") String sort
    );
} 