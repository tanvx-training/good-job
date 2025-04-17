package com.goodjob.company.api.rest;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.company.domain.company.dto.CompanyQuery;
import com.goodjob.company.domain.company.dto.CompanyView;
import com.goodjob.company.domain.company.query.CompanyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for company operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyQueryService companyQueryService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_COMPANY')")
    public ResponseEntity<ApiResponse<PageResponseDTO<CompanyView>>> getAllCompanies(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "companyId,asc") String sort
    ) {
        return ResponseEntity.ok(ApiResponse.success(companyQueryService.getAllCompanies(
                CompanyQuery
                        .builder()
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build())
        ));
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_COMPANY')")
    public ResponseEntity<ApiResponse<CompanyView>> getCompanyById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ApiResponse.success(companyQueryService.getCompanyById(id)));
    }
} 