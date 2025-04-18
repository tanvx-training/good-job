package com.goodjob.company.infrastructure.feign;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.company.infrastructure.feign.industry.IndustryView;
import com.goodjob.company.infrastructure.feign.speciality.SpecialityView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "metadata-service",
        configuration = FeignClientConfig.class
)
public interface MetadataFeignClient {

    @GetMapping("/api/v1/metadata/specialities/batch")
    ResponseEntity<ApiResponse<List<SpecialityView>>> getBatchSpecialities(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/metadata/specialities/{id}")
    ResponseEntity<ApiResponse<SpecialityView>> getSpecialityById(@PathVariable("id") Integer id);


    @GetMapping("/api/v1/metadata/industries/batch")
    ResponseEntity<ApiResponse<List<IndustryView>>> getBatchIndustries(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/metadata/industries/{id}")
    ResponseEntity<ApiResponse<IndustryView>> getIndustryById(@PathVariable("id") Integer id);
}
