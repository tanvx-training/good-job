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
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobHelperImpl implements JobHelper {

    private final MetadataClient metadataClient;

    private final CompanyClient companyClient;

    @Override
    @Cacheable(
            value = "benefits",
            key = "#idList.toString()",
            condition = "#idList != null",
            unless = "#result.isEmpty()"
    )
    public Set<JobBenefitView> getBenefits(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<BenefitView>>> benefitResponse = metadataClient.getBatchBenefits(ids);
            if (benefitResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(benefitResponse.getBody())) {
                return benefitResponse.getBody().getData()
                        .stream()
                        .map(bv -> JobBenefitView.builder()
                                .benefitName(bv.getType())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    @Override
    @Cacheable(
            value = "skills",
            key = "#idList.toString()",
            condition = "#idList != null",
            unless = "#result.isEmpty()"
    )
    public Set<JobSkillView> getSkills(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<SkillView>>> skillResponse = metadataClient.getBatchSkills(ids);
            if (skillResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(skillResponse.getBody())) {
                return skillResponse.getBody().getData()
                        .stream()
                        .map(sv -> JobSkillView.builder()
                                .abbreviation(sv.getAbbreviation())
                                .name(sv.getName())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    @Override
    @Cacheable(
            value = "industries",
            key = "#idList.toString()",
            condition = "#idList != null",
            unless = "#result.isEmpty()"
    )
    public Set<JobIndustryView> getIndustries(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataClient.getBatchIndustries(ids);
            if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(industryResponse.getBody())) {
                return industryResponse.getBody().getData()
                        .stream()
                        .map(iv -> JobIndustryView.builder()
                                .industryName(iv.getName())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    @Override
    @Cacheable(
            value = "company",
            key = "#companyId.toString()",
            condition = "#companyId != null",
            unless = "#result.name == null")
    public JobCompanyView getCompany(Integer companyId) {
        JobCompanyView.JobCompanyViewBuilder companyBuilder = JobCompanyView.builder();
        if (Objects.nonNull(companyId)) {
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
        }
        return companyBuilder.build();
    }
}
