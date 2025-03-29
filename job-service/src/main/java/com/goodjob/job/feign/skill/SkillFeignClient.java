package com.goodjob.job.feign.skill;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.job.feign.FeignClientConfig;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "skill-service",
        configuration = FeignClientConfig.class
)
public interface SkillFeignClient {

    @GetMapping("/api/v1/skills/batch")
    ResponseEntity<ApiResponse<List<SkillView>>> getBatchSkills(@RequestParam("ids") String ids);
}
