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
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CompanyHelperImpl implements CompanyHelper {

    private final MetadataClient metadataClient;

    @Override
    @Cacheable(value = "industries", key = "#idList.toString()", unless = "#result.isEmpty()")
    public CompletableFuture<List<CompanyIndustryView>> getIndustries(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            try {
                ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataClient.getBatchIndustries(ids);
                if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(industryResponse.getBody())) {
                    return CompletableFuture.supplyAsync(() -> industryResponse.getBody().getData()
                            .stream()
                            .map(iv -> CompanyIndustryView.builder()
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
    @Cacheable(value = "specialities", key = "#idList.toString()", unless = "#result.isEmpty()")
    public CompletableFuture<List<CompanySpecialityView>> getSpecialities(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            try {
                ResponseEntity<ApiResponse<List<SpecialityView>>> industryResponse = metadataClient.getBatchSpecialities(ids);
                if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(industryResponse.getBody())) {
                    return CompletableFuture.supplyAsync(() -> industryResponse.getBody().getData()
                            .stream()
                            .map(sv -> CompanySpecialityView.builder()
                                    .id(sv.getId())
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
                System.err.println("Error fetching specialities: " + e.getMessage());
            }
        }
        return CompletableFuture.supplyAsync(Collections::emptyList);
    }
}
