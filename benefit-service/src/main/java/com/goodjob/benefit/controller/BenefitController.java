package com.goodjob.benefit.controller;

import com.goodjob.benefit.command.dto.CreateBenefitCommand;
import com.goodjob.benefit.command.dto.UpdateBenefitCommand;
import com.goodjob.benefit.command.service.BenefitCommandService;
import com.goodjob.benefit.query.dto.BenefitView;
import com.goodjob.benefit.query.service.BenefitQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller for benefit-related endpoints.
 */
@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
@Tag(name = "Benefits", description = "Benefit Management API")
public class BenefitController {

    private final BenefitCommandService benefitCommandService;
    private final BenefitQueryService benefitQueryService;

    /**
     * Create a new benefit.
     *
     * @param command the create benefit command
     * @return the created benefit
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new benefit",
        description = "Creates a new job benefit (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> createBenefit(@Valid @RequestBody CreateBenefitCommand command) {
        Integer benefitId = benefitCommandService.createBenefit(command);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(benefitId)
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    /**
     * Get all benefits.
     *
     * @return list of all benefits
     */
    @GetMapping
    @Operation(
        summary = "Get all benefits",
        description = "Returns a list of all benefits"
    )
    public ResponseEntity<List<BenefitView>> getAllBenefits() {
        List<BenefitView> benefits = benefitQueryService.getAllBenefits();
        return ResponseEntity.ok(benefits);
    }

    /**
     * Get a benefit by ID.
     *
     * @param id the benefit ID
     * @return the benefit
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get benefit by ID",
        description = "Returns a benefit by its ID"
    )
    public ResponseEntity<BenefitView> getBenefitById(@PathVariable Integer id) {
        BenefitView benefit = benefitQueryService.getBenefitById(id);
        return ResponseEntity.ok(benefit);
    }

    /**
     * Update a benefit.
     *
     * @param id the benefit ID
     * @param command the update benefit command
     * @return no content
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update benefit",
        description = "Updates an existing benefit (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> updateBenefit(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateBenefitCommand command) {
        benefitCommandService.updateBenefit(id, command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a benefit.
     *
     * @param id the benefit ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete benefit",
        description = "Deletes a benefit by its ID (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteBenefit(@PathVariable Integer id) {
        benefitCommandService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }
} 