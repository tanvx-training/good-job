package com.goodjob.posting.controller;

import com.goodjob.posting.dto.JobApplicationRequest;
import com.goodjob.posting.dto.JobApplicationResponse;
import com.goodjob.posting.dto.JobPostingRequest;
import com.goodjob.posting.dto.JobPostingResponse;
import com.goodjob.posting.dto.PageResponse;
import com.goodjob.posting.entity.PostingStatus;
import com.goodjob.posting.service.JobApplicationService;
import com.goodjob.posting.service.JobPostingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for job posting operations.
 */
@RestController
@RequestMapping("/postings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Posting Controller", description = "API for managing job postings and applications")
public class PostingController {

    private final JobPostingService jobPostingService;
    private final JobApplicationService jobApplicationService;

    /**
     * Create a new job posting.
     *
     * @param request the job posting request
     * @param jwt     the JWT token
     * @return the created job posting
     */
    @Operation(summary = "Create a new job posting", description = "Creates a new job posting with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Job posting created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobPostingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobPostingResponse> createJobPosting(
            @Valid @RequestBody JobPostingRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Creating job posting: {}", request);
        String employerId = jwt.getSubject();
        JobPostingResponse response = jobPostingService.createJobPosting(request, employerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all job postings with pagination.
     *
     * @param page     the page number
     * @param size     the page size
     * @param sort     the sort field
     * @param direction the sort direction
     * @param status   the job posting status
     * @param keyword  the search keyword
     * @return a page of job postings
     */
    @Operation(summary = "Get all job postings", description = "Returns a paginated list of job postings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job postings",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PageResponse<JobPostingResponse>> getAllJobPostings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(required = false) PostingStatus status,
            @RequestParam(required = false) String keyword) {
        log.info("Getting all job postings: page={}, size={}, sort={}, direction={}, status={}, keyword={}", page, size, sort, direction, status, keyword);
        
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
        PageResponse<JobPostingResponse> response;
        
        if (keyword != null && !keyword.isEmpty()) {
            response = jobPostingService.searchJobPostings(keyword, pageable);
        } else if (status != null) {
            response = jobPostingService.getJobPostingsByStatus(status, pageable);
        } else {
            response = jobPostingService.getAllJobPostings(pageable);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get a job posting by ID.
     *
     * @param id the job posting ID
     * @return the job posting
     */
    @Operation(summary = "Get a job posting by ID", description = "Returns a job posting by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job posting",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobPostingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponse> getJobPostingById(@PathVariable Integer id) {
        log.info("Getting job posting by ID: {}", id);
        JobPostingResponse response = jobPostingService.getJobPostingById(id);
        jobPostingService.incrementViewCount(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Update a job posting.
     *
     * @param id      the job posting ID
     * @param request the job posting request
     * @param jwt     the JWT token
     * @return the updated job posting
     */
    @Operation(summary = "Update a job posting", description = "Updates an existing job posting with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job posting updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobPostingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobPostingResponse> updateJobPosting(
            @PathVariable Integer id,
            @Valid @RequestBody JobPostingRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Updating job posting with ID {}: {}", id, request);
        String employerId = jwt.getSubject();
        JobPostingResponse response = jobPostingService.updateJobPosting(id, request, employerId);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a job posting.
     *
     * @param id  the job posting ID
     * @param jwt the JWT token
     * @return no content
     */
    @Operation(summary = "Delete a job posting", description = "Deletes a job posting by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Job posting deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> deleteJobPosting(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Deleting job posting with ID: {}", id);
        String employerId = jwt.getSubject();
        jobPostingService.deleteJobPosting(id, employerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Apply for a job posting.
     *
     * @param id      the job posting ID
     * @param request the job application request
     * @param jwt     the JWT token
     * @return the created job application
     */
    @Operation(summary = "Apply for a job posting", description = "Creates a new job application for the specified job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Job application created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job posting not found"),
            @ApiResponse(responseCode = "409", description = "Already applied for this job posting")
    })
    @PostMapping("/{id}/apply")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<JobApplicationResponse> applyForJob(
            @PathVariable Integer id,
            @Valid @RequestBody JobApplicationRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Applying for job posting with ID {}: {}", id, request);
        String applicantId = jwt.getSubject();
        JobApplicationResponse response = jobApplicationService.applyForJob(id, request, applicantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all job applications for a job posting.
     *
     * @param id       the job posting ID
     * @param page     the page number
     * @param size     the page size
     * @param jwt      the JWT token
     * @return a page of job applications
     */
    @Operation(summary = "Get all job applications for a job posting", description = "Returns a paginated list of job applications for the specified job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job applications",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @GetMapping("/{id}/applications")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<PageResponse<JobApplicationResponse>> getJobApplicationsByPostingId(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting job applications for job posting with ID {}: page={}, size={}", id, page, size);
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<JobApplicationResponse> response = jobApplicationService.getJobApplicationsByPostingId(id, pageable);
        return ResponseEntity.ok(response);
    }
} 