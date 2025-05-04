package com.goodjob.company.infrastructure.helper.impl;

import com.goodjob.common.api.feign.client.MetadataClient;
import com.goodjob.common.api.feign.dto.industry.IndustryView;
import com.goodjob.common.api.feign.dto.speciality.SpecialityView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.company.domain.company.dto.CompanyIndustryView;
import com.goodjob.company.domain.company.dto.CompanySpecialityView;
import com.goodjob.company.infrastructure.helper.CompanyHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyHelperImpl implements CompanyHelper {

    private final MetadataClient metadataClient;

    @Override
    @Cacheable(value = "industries", key = "#idList.toString()", unless = "#result.isEmpty()")
    public List<CompanyIndustryView> getIndustries(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return fetchAndMap(
                metadataClient.getBatchIndustries(ids),
                IndustryView.class,
                this::toCompanyIndustryView
        );
    }

    @Override
    @Cacheable(value = "specialities", key = "#idList.toString()", unless = "#result.isEmpty()")
    public List<CompanySpecialityView> getSpecialities(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        String ids = idList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return fetchAndMap(
                metadataClient.getBatchSpecialities(ids),
                SpecialityView.class,
                this::toCompanySpecialityView
        );
    }

    private <T, R> List<R> fetchAndMap(ResponseEntity<ApiResponse<List<T>>> responseEntity,
                                       Class<T> type,
                                       java.util.function.Function<T, R> mapper) {
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody().getData().stream()
                    .map(mapper)
                    .toList();
        }
        return Collections.emptyList();
    }

    private CompanyIndustryView toCompanyIndustryView(IndustryView iv) {
        return CompanyIndustryView.builder()
                .id(iv.getId())
                .name(iv.getName())
                .createdOn(iv.getCreatedOn())
                .createdBy(iv.getCreatedBy())
                .lastModifiedOn(iv.getLastModifiedOn())
                .lastModifiedBy(iv.getLastModifiedBy())
                .deleteFlg(iv.isDeleteFlg())
                .build();
    }

    private CompanySpecialityView toCompanySpecialityView(SpecialityView sv) {
        return CompanySpecialityView.builder()
                .id(sv.getId())
                .name(sv.getName())
                .createdOn(sv.getCreatedOn())
                .createdBy(sv.getCreatedBy())
                .lastModifiedOn(sv.getLastModifiedOn())
                .lastModifiedBy(sv.getLastModifiedBy())
                .deleteFlg(sv.isDeleteFlg())
                .build();
    }
}
