package com.goodjob.company.feign.industry;

import com.goodjob.common.dto.ApiResponse;
import java.util.List;

import com.goodjob.company.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "industry-service",
        configuration = FeignClientConfig.class
)
public interface IndustryFeignClient {

    @GetMapping("/api/v1/industries/batch")
    ResponseEntity<ApiResponse<List<IndustryView>>> getBatchIndustries(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/industries/{id}")
    ResponseEntity<ApiResponse<IndustryView>> getIndustryById(@PathVariable("id") Integer id);
}
