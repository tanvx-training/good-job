package com.goodjob.company.controller;

import com.goodjob.company.command.service.CompanyCommandService;
import com.goodjob.company.dto.CompanyCommand;
import com.goodjob.company.dto.CompanyResponse;
import com.goodjob.company.dto.CompanySearchCriteria;
import com.goodjob.company.query.service.CompanyQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for company operations.
 */
@RestController
@RequestMapping("/api/v1/companies")
@Tag(name = "Company", description = "Company management APIs")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyCommandService companyCommandService;
    private final CompanyQueryService companyQueryService;

    public CompanyController(CompanyCommandService companyCommandService, CompanyQueryService companyQueryService) {
        this.companyCommandService = companyCommandService;
        this.companyQueryService = companyQueryService;
    }

    @Operation(
            summary = "Create a new company",
            description = "Create a new company with the provided details. Only employers can create companies.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Company with the same name already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Map<String, Integer>> createCompany(
            @Valid @RequestBody CompanyCommand companyCommand,
            @RequestHeader("X-Employer-ID") Integer employerId) {
        log.info("Creating company with name: {}", companyCommand.getName());
        
        Integer companyId = companyCommandService.createCompany(companyCommand, employerId);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("id", companyId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get company by ID",
            description = "Retrieve a company by its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Company found",
                    content = @Content(schema = @Schema(implementation = CompanyResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id) {
        log.info("Retrieving company with ID: {}", id);
        
        CompanyResponse company = companyQueryService.getCompanyById(id);
        
        return ResponseEntity.ok(company);
    }

    @Operation(
            summary = "Update company",
            description = "Update an existing company. Only the employer who created the company can update it.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> updateCompany(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody CompanyCommand companyCommand,
            @RequestHeader("X-Employer-ID") Integer employerId) {
        log.info("Updating company with ID: {}", id);
        
        companyCommandService.updateCompany(id, companyCommand, employerId);
        
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete company",
            description = "Delete an existing company. Only the employer who created the company can delete it.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id,
            @RequestHeader("X-Employer-ID") Integer employerId) {
        log.info("Deleting company with ID: {}", id);
        
        companyCommandService.deleteCompany(id, employerId);
        
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Verify company",
            description = "Verify a company. Only administrators can verify companies.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company verified successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> verifyCompany(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id) {
        log.info("Verifying company with ID: {}", id);
        
        companyCommandService.verifyCompany(id);
        
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Update company logo",
            description = "Update the logo of an existing company. Only the employer who created the company can update it.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company logo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PatchMapping("/{id}/logo")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> updateCompanyLogo(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id,
            @RequestParam String logoUrl,
            @RequestHeader("X-Employer-ID") Integer employerId) {
        log.info("Updating logo for company with ID: {}", id);
        
        companyCommandService.updateCompanyLogo(id, logoUrl, employerId);
        
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Update company banner",
            description = "Update the banner of an existing company. Only the employer who created the company can update it.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company banner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PatchMapping("/{id}/banner")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> updateCompanyBanner(
            @Parameter(description = "Company ID", required = true)
            @PathVariable Integer id,
            @RequestParam String bannerUrl,
            @RequestHeader("X-Employer-ID") Integer employerId) {
        log.info("Updating banner for company with ID: {}", id);
        
        companyCommandService.updateCompanyBanner(id, bannerUrl, employerId);
        
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get companies by employer ID",
            description = "Retrieve companies created by a specific employer",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/employer/{employerId}")
    @PreAuthorize("hasRole('EMPLOYER') and #employerId == authentication.principal.id")
    public ResponseEntity<Page<CompanyResponse>> getCompaniesByEmployerId(
            @Parameter(description = "Employer ID", required = true)
            @PathVariable Integer employerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Retrieving companies for employer with ID: {}", employerId);
        
        Page<CompanyResponse> companies = companyQueryService.getCompaniesByEmployerId(
                employerId, page, size, sort, direction);
        
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Get verified companies",
            description = "Retrieve all verified companies"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully")
    })
    @GetMapping("/verified")
    public ResponseEntity<Page<CompanyResponse>> getVerifiedCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Retrieving verified companies");
        
        Page<CompanyResponse> companies = companyQueryService.getVerifiedCompanies(
                page, size, sort, direction);
        
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Search companies",
            description = "Search companies based on various criteria"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully")
    })
    @PostMapping("/search")
    public ResponseEntity<Page<CompanyResponse>> searchCompanies(
            @RequestBody CompanySearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Searching companies with criteria: {}", criteria);
        
        Page<CompanyResponse> companies = companyQueryService.searchCompanies(
                criteria, page, size, sort, direction);
        
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Get top rated companies",
            description = "Retrieve top rated companies"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully")
    })
    @GetMapping("/top-rated")
    public ResponseEntity<List<CompanyResponse>> getTopRatedCompanies(
            @RequestParam(defaultValue = "5") int limit) {
        log.info("Retrieving top {} rated companies", limit);
        
        List<CompanyResponse> companies = companyQueryService.getTopRatedCompanies(limit);
        
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Get recently added companies",
            description = "Retrieve recently added companies"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<CompanyResponse>> getRecentlyAddedCompanies(
            @RequestParam(defaultValue = "5") int limit) {
        log.info("Retrieving {} recently added companies", limit);
        
        List<CompanyResponse> companies = companyQueryService.getRecentlyAddedCompanies(limit);
        
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Get company by name",
            description = "Retrieve a company by its name"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Company found",
                    content = @Content(schema = @Schema(implementation = CompanyResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<CompanyResponse> getCompanyByName(
            @Parameter(description = "Company name", required = true)
            @PathVariable String name) {
        log.info("Retrieving company with name: {}", name);
        
        CompanyResponse company = companyQueryService.getCompanyByName(name);
        
        return ResponseEntity.ok(company);
    }
} 