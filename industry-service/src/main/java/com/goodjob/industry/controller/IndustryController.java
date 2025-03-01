package com.goodjob.industry.controller;

import com.goodjob.industry.command.dto.CreateIndustryCommand;
import com.goodjob.industry.command.dto.UpdateIndustryCommand;
import com.goodjob.industry.command.service.IndustryCommandService;
import com.goodjob.industry.query.dto.IndustryView;
import com.goodjob.industry.query.service.IndustryQueryService;
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
 * Controller for industry-related endpoints.
 */
@RestController
@RequestMapping("/industries")
@RequiredArgsConstructor
@Tag(name = "Industries", description = "Industry Management API")
public class IndustryController {

    private final IndustryCommandService industryCommandService;
    private final IndustryQueryService industryQueryService;

    /**
     * Create a new industry.
     *
     * @param command the create industry command
     * @return the created industry
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new industry",
        description = "Creates a new industry category (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> createIndustry(@Valid @RequestBody CreateIndustryCommand command) {
        Integer industryId = industryCommandService.createIndustry(command);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(industryId)
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    /**
     * Get all industries.
     *
     * @return list of all industries
     */
    @GetMapping
    @Operation(
        summary = "Get all industries",
        description = "Returns a list of all industries"
    )
    public ResponseEntity<List<IndustryView>> getAllIndustries() {
        List<IndustryView> industries = industryQueryService.getAllIndustries();
        return ResponseEntity.ok(industries);
    }

    /**
     * Get an industry by ID.
     *
     * @param id the industry ID
     * @return the industry
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get industry by ID",
        description = "Returns an industry by its ID"
    )
    public ResponseEntity<IndustryView> getIndustryById(@PathVariable Integer id) {
        IndustryView industry = industryQueryService.getIndustryById(id);
        return ResponseEntity.ok(industry);
    }

    /**
     * Update an industry.
     *
     * @param id the industry ID
     * @param command the update industry command
     * @return no content
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update industry",
        description = "Updates an existing industry (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> updateIndustry(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateIndustryCommand command) {
        industryCommandService.updateIndustry(id, command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete an industry.
     *
     * @param id the industry ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete industry",
        description = "Deletes an industry by its ID (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteIndustry(@PathVariable Integer id) {
        industryCommandService.deleteIndustry(id);
        return ResponseEntity.noContent().build();
    }
} 