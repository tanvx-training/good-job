package com.goodjob.job.infrastructure.feign;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.job.infrastructure.feign.company.CompanyView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        configuration = FeignClientConfig.class
)
public interface CompanyFeignClient {

    @GetMapping("/api/v1/companies/{id}")
    ResponseEntity<ApiResponse<CompanyView>> getCompanyById(@PathVariable("id") Integer id);
}
