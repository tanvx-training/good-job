package com.goodjob.job.infrastructure.helper.impl;

import com.goodjob.common.api.feign.client.CompanyClient;
import com.goodjob.common.api.feign.client.MetadataClient;
import com.goodjob.common.api.feign.dto.benefit.BenefitView;
import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.api.feign.dto.industry.IndustryView;
import com.goodjob.common.api.feign.dto.skill.SkillView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;
import com.goodjob.job.infrastructure.helper.JobHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobHelperImpl implements JobHelper {

    private final MetadataClient metadataClient;
    private final CompanyClient companyClient;

    @Override
    @Cacheable(value = "benefits", key = "#idList.toString()", unless = "#result.isEmpty()")
    public List<JobBenefitView> getBenefits(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(","));
        return fetchAndMap(metadataClient.getBatchBenefits(ids), BenefitView.class, this::toJobBenefitView);
    }

    @Override
    @Cacheable(value = "skills", key = "#idList.toString()", unless = "#result.isEmpty()")
    public List<JobSkillView> getSkills(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(","));
        return fetchAndMap(metadataClient.getBatchSkills(ids), SkillView.class, this::toJobSkillView);
    }

    @Override
    @Cacheable(value = "industries", key = "#idList.toString()", unless = "#result.isEmpty()")
    public List<JobIndustryView> getIndustries(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(","));
        return fetchAndMap(metadataClient.getBatchIndustries(ids), IndustryView.class, this::toJobIndustryView);
    }

    @Override
    @Cacheable(value = "companies", key = "#companyId", unless = "#result.name == null")
    public JobCompanyView getCompany(Integer companyId) {
        if (companyId == null) {
            return JobCompanyView.builder().build();
        }
        ApiResponse<CompanyView> companyResponse = companyClient.getCompanyById(companyId);
        if (companyResponse.isSuccess() && companyResponse.getData() != null) {
            CompanyView company = companyResponse.getData();
            return JobCompanyView
                    .builder()
                    .name(company.getName())
                    .description(company.getDescription())
                    .companySize(company.getCompanySize())
                    .address(company.getAddress())
                    .url(company.getUrl())
                    .build();
        }
        return JobCompanyView.builder().build();
    }

    private <T, R> List<R> fetchAndMap(ResponseEntity<ApiResponse<List<T>>> responseEntity, Class<T> type, java.util.function.Function<T, R> mapper) {
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody().getData().stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private JobBenefitView toJobBenefitView(BenefitView bv) {
        return JobBenefitView.builder()
                .id(bv.getId())
                .type(bv.getType())
                .createdOn(bv.getCreatedOn())
                .createdBy(bv.getCreatedBy())
                .lastModifiedOn(bv.getLastModifiedOn())
                .lastModifiedBy(bv.getLastModifiedBy())
                .deleteFlg(bv.isDeleteFlg())
                .build();
    }

    private JobSkillView toJobSkillView(SkillView sv) {
        return JobSkillView.builder()
                .id(sv.getId())
                .abbreviation(sv.getAbbreviation())
                .name(sv.getName())
                .createdOn(sv.getCreatedOn())
                .createdBy(sv.getCreatedBy())
                .lastModifiedOn(sv.getLastModifiedOn())
                .lastModifiedBy(sv.getLastModifiedBy())
                .deleteFlg(sv.isDeleteFlg())
                .build();
    }

    private JobIndustryView toJobIndustryView(IndustryView iv) {
        return JobIndustryView.builder()
                .id(iv.getId())
                .name(iv.getName())
                .createdOn(iv.getCreatedOn())
                .createdBy(iv.getCreatedBy())
                .lastModifiedOn(iv.getLastModifiedOn())
                .lastModifiedBy(iv.getLastModifiedBy())
                .deleteFlg(iv.isDeleteFlg())
                .build();
    }
}