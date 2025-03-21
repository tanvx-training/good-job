package com.goodjob.company.feign.industry;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "industry-service")
public interface IndustryFeignClient {

    @GetMapping("/api/v1/industries")
    ResponseEntity<ApiResponse<PageResponseDTO<IndustryView>>> getAllIndustries(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "industryId,asc") String sort
    );

    @GetMapping("/api/v1/industries/{id}")
    ResponseEntity<ApiResponse<IndustryView>> getIndustryById(@PathVariable("id") Integer id);
}
