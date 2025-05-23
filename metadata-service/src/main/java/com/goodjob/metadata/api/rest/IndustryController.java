package com.goodjob.metadata.api.rest;

import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.metadata.domain.industry.dto.CreateIndustryCommand;
import com.goodjob.metadata.domain.industry.dto.UpdateIndustryCommand;
import com.goodjob.metadata.domain.industry.command.IndustryCommandService;
import com.goodjob.metadata.domain.industry.dto.IndustryQuery;
import com.goodjob.metadata.domain.industry.dto.IndustryView;
import com.goodjob.metadata.domain.industry.query.IndustryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for industry-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/metadata/industries")
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
    public ResponseEntity<ApiResponse<PageResponseDTO<IndustryView>>> getAllIndustries(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "industryId,asc") String sort
    ) {
        return ResponseEntity.ok(ApiResponse.success(industryQueryService.getAllIndustries(
                IndustryQuery.builder()
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build()))
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
    public ResponseEntity<ApiResponse<IndustryView>> getIndustryById(@PathVariable Integer id) {
        IndustryView industry = industryQueryService.getIndustryById(id);
        return ResponseEntity.ok(ApiResponse.success(industry));
    }

    /**
     * Update an industry.
     *
     * @param id      the industry ID
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

    @GetMapping("/batch")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_INDUSTRY')")
    public ResponseEntity<ApiResponse<List<IndustryView>>> getBatchIndustry(
            @RequestParam("ids") String ids
    ) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(industryQueryService.getAllByIdList(idList)));
    }
} 