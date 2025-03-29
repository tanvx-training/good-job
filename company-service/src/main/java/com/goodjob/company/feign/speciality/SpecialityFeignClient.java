package com.goodjob.company.feign.speciality;

import com.goodjob.common.dto.response.ApiResponse;
import java.util.List;

import com.goodjob.company.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "speciality-service",
        configuration = FeignClientConfig.class
)
public interface SpecialityFeignClient {

    @GetMapping("/api/v1/specialities/batch")
    ResponseEntity<ApiResponse<List<SpecialityView>>> getBatchSpecialities(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/specialities/{id}")
    ResponseEntity<ApiResponse<SpecialityView>> getSpecialityById(@PathVariable("id") Integer id);
}
