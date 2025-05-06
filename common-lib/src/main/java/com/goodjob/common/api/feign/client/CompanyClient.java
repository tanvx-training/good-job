package com.goodjob.common.api.feign.client;

import com.goodjob.common.api.feign.dto.company.CompanyView;
import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.api.feign.config.FeignClientConfig;
import com.goodjob.common.api.feign.fallback.CompanyClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign client for company-service.
 * This interface defines methods to call the company-service APIs.
 */
@FeignClient(
    name = "company-service",
    configuration = FeignClientConfig.class,
    fallback = CompanyClientFallback.class
)
public interface CompanyClient {

    /**
     * Get company by ID.
     *
     * @param id the company ID
     * @return ApiResponse containing the company details
     */
    @GetMapping("/api/v1/companies/{id}")
    ResponseEntity<ApiResponse<CompanyView>> getCompanyById(@PathVariable("id") Integer id);

    /**
     * Get list company by list ID.
     *
     * @param ids list the company ID
     * @return ApiResponse containing the list company details
     */
    @GetMapping("/api/v1/companies/batch")
    ResponseEntity<ApiResponse<List<CompanyView>>> getBatchCompanies(@RequestParam("ids") String ids);

    /**
     * Get all companies with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @param sort the sort criteria (format: property,direction - e.g., "companyId,asc")
     * @return ApiResponse containing paginated company list
     */
    @GetMapping("/api/v1/companies")
    ResponseEntity<ApiResponse<PageResponseDTO<CompanyView>>> getAllCompanies(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "20") Integer size,
        @RequestParam(value = "sort", defaultValue = "companyId,asc") String sort
    );
} 