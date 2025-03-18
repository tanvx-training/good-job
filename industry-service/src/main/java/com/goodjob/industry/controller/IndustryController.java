package com.goodjob.industry.controller;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.industry.command.dto.CreateIndustryCommand;
import com.goodjob.industry.command.dto.UpdateIndustryCommand;
import com.goodjob.industry.command.service.IndustryCommandService;
import com.goodjob.industry.query.dto.IndustryQuery;
import com.goodjob.industry.query.dto.IndustryView;
import com.goodjob.industry.query.service.IndustryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller for industry-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/industries")
@RequiredArgsConstructor
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_INDUSTRY')")
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_INDUSTRY')")
    public ResponseEntity<PageResponseDTO<IndustryView>> getAllIndustries(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "industryId,asc") String sort
    ) {
        return ResponseEntity.ok(industryQueryService.getAllIndustries(
                IndustryQuery.builder()
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build())
        );
    }

    /**
     * Get an industry by ID.
     *
     * @param id the industry ID
     * @return the industry
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_INDUSTRY')")
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('UPDATE_INDUSTRY')")
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELETE_INDUSTRY')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteIndustry(@PathVariable Integer id) {
        industryCommandService.deleteIndustry(id);
        return ResponseEntity.noContent().build();
    }
} 