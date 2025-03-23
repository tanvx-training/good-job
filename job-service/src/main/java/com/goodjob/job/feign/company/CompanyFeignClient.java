package com.goodjob.job.feign.company;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.job.feign.FeignClientConfig;
import com.goodjob.job.feign.industry.IndustryView;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "company-service",
        configuration = FeignClientConfig.class
)
public interface CompanyFeignClient {

    @GetMapping("/api/v1/companies/{id}")
    ResponseEntity<ApiResponse<IndustryView>> getIndustryById(@PathVariable("id") Integer id);
}
