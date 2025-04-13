package com.goodjob.posting.feign;

import com.goodjob.common.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "job-service",
        configuration = FeignClientConfig.class
)
public interface JobFeignClient {

    @GetMapping("/api/v1/jobs/{id}")
    ResponseEntity<ApiResponse<JobView>> getJobById(@PathVariable("id") Long id);
}
