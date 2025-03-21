package com.goodjob.company.feign.speciality;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "speciality-service")
public interface SpecialityFeignClient {

    @GetMapping("/api/v1/specialities")
    ResponseEntity<ApiResponse<PageResponseDTO<SpecialityView>>> getAllSpecialities(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "specialityId,asc") String sort
    );

    @GetMapping("/api/v1/specialities/{id}")
    ResponseEntity<ApiResponse<SpecialityView>> getSpecialityById(@PathVariable("id") Integer id);
}
