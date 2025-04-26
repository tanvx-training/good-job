package com.goodjob.job.infrastructure.helper.impl;

import com.goodjob.common.api.feign.client.MetadataClient;
import com.goodjob.common.api.feign.dto.benefit.BenefitView;
import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.api.feign.dto.industry.IndustryView;
import com.goodjob.common.api.feign.dto.skill.SkillView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.api.feign.client.CompanyClient;
import com.goodjob.job.infrastructure.helper.JobHelper;
import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class JobHelperImpl implements JobHelper {

    private final MetadataClient metadataClient;
    private final CompanyClient companyClient;

    @Override
    @Cacheable(value = "benefits", key = "#idList.toString()", unless = "#result.isEmpty()")
    public CompletableFuture<List<JobBenefitView>> getBenefits(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream().map(String::valueOf).toList());
            try {
                ResponseEntity<ApiResponse<List<BenefitView>>> benefitResponse = metadataClient.getBatchBenefits(ids);
                if (benefitResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(benefitResponse.getBody())) {
                    return CompletableFuture.supplyAsync(() -> benefitResponse.getBody().getData()
                            .stream()
                            .map(bv -> JobBenefitView.builder()
                                    .id(bv.getId())
                                    .type(bv.getType())
                                    .createdOn(bv.getCreatedOn())
                                    .createdBy(bv.getCreatedBy())
                                    .lastModifiedOn(bv.getLastModifiedOn())
                                    .lastModifiedBy(bv.getLastModifiedBy())
                                    .deleteFlg(bv.isDeleteFlg())
                                    .build())
                            .toList());
                }
            } catch (Exception e) {
                // Ghi log lỗi nếu cần
                System.err.println("Error fetching benefits: " + e.getMessage());
            }
        }
        return CompletableFuture.supplyAsync(Collections::emptyList);
    }

    @Override
    @Cacheable(value = "skills", key = "#idList.toString()", unless = "#result.isEmpty()")
    public CompletableFuture<List<JobSkillView>> getSkills(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream().map(String::valueOf).toList());
            try {
                ResponseEntity<ApiResponse<List<SkillView>>> skillResponse = metadataClient.getBatchSkills(ids);
                if (skillResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(skillResponse.getBody())) {
                    return CompletableFuture.supplyAsync(() -> skillResponse.getBody().getData()
                            .stream()
                            .map(sv -> JobSkillView.builder()
                                    .id(sv.getId())
                                    .abbreviation(sv.getAbbreviation())
                                    .name(sv.getName())
                                    .createdOn(sv.getCreatedOn())
                                    .createdBy(sv.getCreatedBy())
                                    .lastModifiedOn(sv.getLastModifiedOn())
                                    .lastModifiedBy(sv.getLastModifiedBy())
                                    .deleteFlg(sv.isDeleteFlg())
                                    .build())
                            .toList());
                }
            } catch (Exception e) {
                // Ghi log lỗi nếu cần
                System.err.println("Error fetching skills: " + e.getMessage());
            }
        }
        return CompletableFuture.supplyAsync(Collections::emptyList);
    }

    @Override
    @Cacheable(value = "industries", key = "#idList.toString()", unless = "#result.isEmpty()")
    public CompletableFuture<List<JobIndustryView>> getIndustries(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream().map(String::valueOf).toList());

            try {
                ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataClient.getBatchIndustries(ids);
                if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(industryResponse.getBody())) {
                    return CompletableFuture.supplyAsync(() -> industryResponse.getBody().getData()
                            .stream()
                            .map(iv -> JobIndustryView.builder()
                                    .id(iv.getId())
                                    .name(iv.getName())
                                    .createdOn(iv.getCreatedOn())
                                    .createdBy(iv.getCreatedBy())
                                    .lastModifiedOn(iv.getLastModifiedOn())
                                    .lastModifiedBy(iv.getLastModifiedBy())
                                    .deleteFlg(iv.isDeleteFlg())
                                    .build())
                            .toList());
                }
            } catch (Exception e) {
                // Ghi log lỗi nếu cần
                System.err.println("Error fetching industries: " + e.getMessage());
            }
        }
        return CompletableFuture.supplyAsync(Collections::emptyList);
    }

    @Override
    @Cacheable(value = "companies", key = "#companyId", unless = "#result == null")
    public CompletableFuture<JobCompanyView> getCompany(Integer companyId) {
        JobCompanyView.JobCompanyViewBuilder companyBuilder = JobCompanyView.builder();
        if (Objects.nonNull(companyId)) {
            try {
                ApiResponse<CompanyView> companyResponse = companyClient.getCompanyById(companyId.longValue());
                if (companyResponse.isSuccess() && Objects.nonNull(companyResponse.getData())) {
                    CompanyView company = companyResponse.getData();
                    companyBuilder
                            .name(company.getName())
                            .description(company.getDescription())
                            .companySize(company.getCompanySize() != null ? company.getCompanySize() : null)
                            .address(company.getAddress())
                            .url(company.getUrl());
                }
            } catch (Exception e) {
                // Ghi log lỗi nếu cần
                System.err.println("Error fetching company: " + e.getMessage());
            }
        }
        return CompletableFuture.supplyAsync(companyBuilder::build);
    }
}