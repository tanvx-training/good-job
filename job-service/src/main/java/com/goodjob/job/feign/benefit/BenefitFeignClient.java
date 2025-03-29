package com.goodjob.job.feign.benefit;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.job.feign.FeignClientConfig;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "benefit-service",
        configuration = FeignClientConfig.class
)
public interface BenefitFeignClient {

    @GetMapping("/api/v1/benefits/batch")
    ResponseEntity<ApiResponse<List<BenefitView>>> getBatchBenefits(@RequestParam("ids") String ids);
}
