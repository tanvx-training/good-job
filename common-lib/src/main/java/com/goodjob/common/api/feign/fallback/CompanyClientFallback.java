package com.goodjob.common.api.feign.fallback;

import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.api.feign.client.CompanyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Fallback implementation for CompanyClient.
 * This class provides fallback methods for CompanyClient in case of service failure.
 */
@Slf4j
@Component
public class CompanyClientFallback implements CompanyClient {

    @Override
    public ResponseEntity<ApiResponse<CompanyView>> getCompanyById(Integer id) {
        log.error("Fallback: Unable to get company with ID {}", id);
        return ResponseEntity.ok(ApiResponse.error("Company service is not available"));
    }

    @Override
    public ResponseEntity<ApiResponse<List<CompanyView>>> getBatchCompanies(String ids) {
        return ResponseEntity.ok(ApiResponse.error("Company service is not available"));
    }

    @Override
    public ResponseEntity<ApiResponse<PageResponseDTO<CompanyView>>> getAllCompanies(Integer page, Integer size, String sort) {
        log.error("Fallback: Unable to get companies list (page={}, size={})", page, size);
        
        PageResponseDTO<CompanyView> emptyPage = new PageResponseDTO<>(
            Collections.emptyList(),
            0,
            0,
            page,
            size,
            true,
            true,
            true
        );
        
        return ResponseEntity.ok(ApiResponse.error("Company service is not available", emptyPage));
    }
} 