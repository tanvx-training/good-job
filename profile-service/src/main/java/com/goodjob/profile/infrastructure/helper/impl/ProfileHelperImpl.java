package com.goodjob.profile.infrastructure.helper.impl;

import com.goodjob.common.api.feign.client.CompanyClient;
import com.goodjob.common.api.feign.client.MetadataClient;
import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.api.feign.dto.skill.SkillView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.profile.domain.client.ExperienceCompanyView;
import com.goodjob.profile.domain.client.ProfileSkillView;
import com.goodjob.profile.infrastructure.helper.ProfileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileHelperImpl implements ProfileHelper {

    private final MetadataClient metadataClient;

    private final CompanyClient companyClient;

    @Override
    public List<ProfileSkillView> getSkills(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(","));
        return fetchAndMap(metadataClient.getBatchSkills(ids), SkillView.class, this::toJobSkillView);
    }

    @Override
    public List<ExperienceCompanyView> getBatchCompanies(List<Integer> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return Collections.emptyList();
        }
        String ids = companyIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        return fetchAndMap(companyClient.getBatchCompanies(ids), CompanyView.class, this::toExperienceCompanyView);
    }

    private ExperienceCompanyView toExperienceCompanyView(CompanyView cv) {
        return ExperienceCompanyView.builder()
                .id(cv.getCompanyId()) // Giả sử CompanyView có trường id
                .name(cv.getName())
                .description(cv.getDescription())
                .companySize(cv.getCompanySize())
                .address(cv.getAddress())
                .url(cv.getUrl())
                .build();
    }

    private ProfileSkillView toJobSkillView(SkillView sv) {
        return ProfileSkillView.builder()
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

    private <T, R> List<R> fetchAndMap(ResponseEntity<ApiResponse<List<T>>> responseEntity, Class<T> type, java.util.function.Function<T, R> mapper) {
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody().getData().stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}