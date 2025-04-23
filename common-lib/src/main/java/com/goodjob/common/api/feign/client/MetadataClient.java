package com.goodjob.common.api.feign.client;

import com.goodjob.common.api.feign.config.FeignClientConfig;
import com.goodjob.common.api.feign.dto.benefit.BenefitView;
import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.api.feign.dto.industry.IndustryView;
import com.goodjob.common.api.feign.dto.skill.SkillView;
import com.goodjob.common.application.dto.response.ApiResponse;
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
public interface MetadataClient {
    @GetMapping("/api/v1/metadata/skills/batch")
    ResponseEntity<ApiResponse<List<SkillView>>> getBatchSkills(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/metadata/industries/batch")
    ResponseEntity<ApiResponse<List<IndustryView>>> getBatchIndustries(@RequestParam("ids") String ids);

    @GetMapping("/api/v1/companies/{id}")
    ResponseEntity<ApiResponse<CompanyView>> getCompanyById(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/metadata/benefits/batch")
    ResponseEntity<ApiResponse<List<BenefitView>>> getBatchBenefits(@RequestParam("ids") String ids);
}
