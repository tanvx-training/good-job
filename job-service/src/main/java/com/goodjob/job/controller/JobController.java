package com.goodjob.job.controller;

import com.goodjob.job.command.dto.CreateJobCommand;
import com.goodjob.job.command.dto.UpdateJobCommand;
import com.goodjob.job.command.service.JobCommandService;
import com.goodjob.job.entity.JobStatus;
import com.goodjob.job.query.dto.JobSearchCriteria;
import com.goodjob.job.query.dto.JobView;
import com.goodjob.job.query.service.JobQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * REST controller for managing job postings.
 */
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Job API", description = "API for job operations")
public class JobController {

    private final JobCommandService jobCommandService;
    private final JobQueryService jobQueryService;

    // Query Operations

    /**
     * Get all jobs with pagination.
     *
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping
    @Operation(summary = "Get all jobs", description = "Returns a paginated list of all active jobs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getAllJobs(
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs");
        return ResponseEntity.ok(jobQueryService.getJobsByStatus(JobStatus.ACTIVE, pageable));
    }

    /**
     * Get a job by ID.
     *
     * @param id the job ID
     * @return the job view
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a job by ID",
            description = "Retrieve detailed information about a job by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Job found",
                    content = @Content(schema = @Schema(implementation = JobView.class))
            ),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<JobView> getJobById(@PathVariable Integer id) {
        JobView job = jobQueryService.getJobById(id);
        
        // Increment job views
        jobCommandService.incrementJobViews(id);
        
        return ResponseEntity.ok(job);
    }

    /**
     * Search for jobs based on criteria with pagination.
     *
     * @param criteria the search criteria
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/search")
    @Operation(summary = "Search for jobs", description = "Returns a paginated list of jobs matching the search criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> searchJobs(
            @Valid @RequestBody @Parameter(description = "Search criteria") JobSearchCriteria criteria,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to search jobs with criteria: {}", criteria);
        return ResponseEntity.ok(jobQueryService.searchJobs(criteria, pageable));
    }

    /**
     * Get all jobs by employer ID with pagination.
     *
     * @param jwt      the JWT token
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/employer")
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(
            summary = "Get jobs by employer",
            description = "Retrieve jobs posted by the authenticated employer",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jobs retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Page<JobView>> getJobsByEmployer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) JobStatus status,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        
        String employerId = jwt.getSubject();
        Page<JobView> jobs;
        
        if (status != null) {
            jobs = jobQueryService.getJobsByEmployerIdAndStatus(employerId, status, page, size, sort, direction);
        } else {
            jobs = jobQueryService.getJobsByEmployerId(employerId, page, size, sort, direction);
        }
        
        return ResponseEntity.ok(jobs);
    }

    /**
     * Get all jobs by location with pagination.
     *
     * @param location the job location
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/location/{location}")
    @Operation(summary = "Get all jobs by location", description = "Returns a paginated list of jobs in the specified location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getJobsByLocation(
            @PathVariable @Parameter(description = "Job location") String location,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs by location: {}", location);
        return ResponseEntity.ok(jobQueryService.getJobsByLocation(location, pageable));
    }

    /**
     * Get all jobs by company name with pagination.
     *
     * @param companyName the company name
     * @param pageable    the pagination information
     * @return a page of job views
     */
    @GetMapping("/company/{companyName}")
    @Operation(summary = "Get all jobs by company name", description = "Returns a paginated list of jobs posted by the specified company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getJobsByCompanyName(
            @PathVariable @Parameter(description = "Company name") String companyName,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs by company name: {}", companyName);
        return ResponseEntity.ok(jobQueryService.getJobsByCompanyName(companyName, pageable));
    }

    /**
     * Get all jobs by title with pagination.
     *
     * @param title    the job title
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/title/{title}")
    @Operation(summary = "Get all jobs by title", description = "Returns a paginated list of jobs with the specified title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getJobsByTitle(
            @PathVariable @Parameter(description = "Job title") String title,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs by title: {}", title);
        return ResponseEntity.ok(jobQueryService.getJobsByTitle(title, pageable));
    }

    /**
     * Get all jobs by skill ID with pagination.
     *
     * @param skillId  the skill ID
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/skill/{skillId}")
    @Operation(summary = "Get all jobs by skill", description = "Returns a paginated list of jobs requiring the specified skill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getJobsBySkillId(
            @PathVariable @Parameter(description = "Skill ID") Integer skillId,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs by skill ID: {}", skillId);
        return ResponseEntity.ok(jobQueryService.getJobsBySkillId(skillId, pageable));
    }

    /**
     * Get all jobs by industry ID with pagination.
     *
     * @param industryId the industry ID
     * @param pageable   the pagination information
     * @return a page of job views
     */
    @GetMapping("/industry/{industryId}")
    @Operation(summary = "Get all jobs by industry", description = "Returns a paginated list of jobs in the specified industry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<JobView>> getJobsByIndustryId(
            @PathVariable @Parameter(description = "Industry ID") Integer industryId,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get all jobs by industry ID: {}", industryId);
        return ResponseEntity.ok(jobQueryService.getJobsByIndustryId(industryId, pageable));
    }

    /**
     * Get recommended jobs for a user based on their skills.
     *
     * @param jwt      the JWT token
     * @param pageable the pagination information
     * @return a page of job views
     */
    @GetMapping("/recommended")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @Operation(summary = "Get recommended jobs", description = "Returns a paginated list of jobs recommended for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Page<JobView>> getRecommendedJobs(
            @AuthenticationPrincipal Jwt jwt,
            @PageableDefault(size = 10) Pageable pageable) {
        String userId = jwt.getSubject();
        log.info("REST request to get recommended jobs for user with ID: {}", userId);
        return ResponseEntity.ok(jobQueryService.getRecommendedJobs(userId, pageable));
    }

    // Command Operations

    /**
     * Create a new job posting.
     *
     * @param command the job creation command
     * @param jwt     the JWT token of the authenticated user
     * @return the ID of the created job
     */
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(
            summary = "Create a new job posting",
            description = "Create a new job posting with the provided details",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Job created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Map<String, Integer>> createJob(
            @Valid @RequestBody CreateJobCommand command,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        
        String employerId = jwt.getSubject();
        Integer jobId = jobCommandService.createJob(command, employerId);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", jobId));
    }

    /**
     * Update an existing job posting.
     *
     * @param id      the job ID
     * @param command the job update command
     * @param jwt     the JWT token of the authenticated user
     * @return the ID of the updated job
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(
            summary = "Update a job posting",
            description = "Update an existing job posting with the provided details",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<Map<String, Integer>> updateJob(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateJobCommand command,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        
        String employerId = jwt.getSubject();
        Integer jobId = jobCommandService.updateJob(id, command, employerId);
        
        return ResponseEntity.ok(Map.of("id", jobId));
    }

    /**
     * Delete a job posting.
     *
     * @param id  the job ID
     * @param jwt the JWT token of the authenticated user
     * @return a response with no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a job posting",
            description = "Delete an existing job posting",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Job deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<Void> deleteJob(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        
        String employerId = jwt.getSubject();
        jobCommandService.deleteJob(id, employerId);
        
        return ResponseEntity.noContent().build();
    }

    /**
     * Update the status of a job posting.
     *
     * @param id     the job ID
     * @param status the new status
     * @param jwt    the JWT token of the authenticated user
     * @return the ID of the updated job
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(
            summary = "Update job status",
            description = "Update the status of an existing job posting",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<Map<String, Integer>> updateJobStatus(
            @PathVariable Integer id,
            @RequestParam JobStatus status,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        
        String employerId = jwt.getSubject();
        Integer jobId = jobCommandService.updateJobStatus(id, status, employerId);
        
        return ResponseEntity.ok(Map.of("id", jobId));
    }

    /**
     * Increment the application count of a job posting.
     *
     * @param id  the job ID
     * @return the new application count
     */
    @PostMapping("/{id}/applications")
    @Operation(
            summary = "Increment job applications",
            description = "Increment the application count for a job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application count incremented successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<Map<String, Integer>> incrementJobApplications(@PathVariable Integer id) {
        Integer applications = jobCommandService.incrementJobApplications(id);
        
        return ResponseEntity.ok(Map.of("applications", applications));
    }
} 