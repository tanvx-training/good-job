package com.goodjob.posting.controller;

import com.goodjob.posting.dto.JobApplicationResponse;
import com.goodjob.posting.dto.PageResponse;
import com.goodjob.posting.entity.ApplicationStatus;
import com.goodjob.posting.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for job application operations.
 */
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Application Controller", description = "API for managing job applications")
public class ApplicationController {

    private final JobApplicationService jobApplicationService;

    /**
     * Get a job application by ID.
     *
     * @param id  the job application ID
     * @param jwt the JWT token
     * @return the job application
     */
    @Operation(summary = "Get a job application by ID", description = "Returns a job application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job application",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job application not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYER', 'APPLICANT')")
    public ResponseEntity<JobApplicationResponse> getJobApplicationById(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting job application by ID: {}", id);
        JobApplicationResponse response = jobApplicationService.getJobApplicationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all job applications for the authenticated applicant.
     *
     * @param page the page number
     * @param size the page size
     * @param jwt  the JWT token
     * @return a page of job applications
     */
    @Operation(summary = "Get all job applications for the authenticated applicant", description = "Returns a paginated list of job applications for the authenticated applicant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job applications",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<PageResponse<JobApplicationResponse>> getMyJobApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting job applications for authenticated applicant: page={}, size={}", page, size);
        String applicantId = jwt.getSubject();
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<JobApplicationResponse> response = jobApplicationService.getJobApplicationsByApplicantId(applicantId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all job applications for the authenticated employer.
     *
     * @param page the page number
     * @param size the page size
     * @param jwt  the JWT token
     * @return a page of job applications
     */
    @Operation(summary = "Get all job applications for the authenticated employer", description = "Returns a paginated list of job applications for the authenticated employer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job applications",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/employer-applications")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<PageResponse<JobApplicationResponse>> getEmployerJobApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting job applications for authenticated employer: page={}, size={}", page, size);
        String employerId = jwt.getSubject();
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<JobApplicationResponse> response = jobApplicationService.getJobApplicationsByEmployerId(employerId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Update the status of a job application.
     *
     * @param id     the job application ID
     * @param status the new status
     * @param jwt    the JWT token
     * @return the updated job application
     */
    @Operation(summary = "Update the status of a job application", description = "Updates the status of a job application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job application status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job application not found")
    })
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobApplicationResponse> updateJobApplicationStatus(
            @PathVariable Integer id,
            @RequestParam ApplicationStatus status,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Updating job application status: id={}, status={}", id, status);
        String employerId = jwt.getSubject();
        JobApplicationResponse response = jobApplicationService.updateJobApplicationStatus(id, status, employerId);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark a job application as viewed by the employer.
     *
     * @param id  the job application ID
     * @param jwt the JWT token
     * @return the updated job application
     */
    @Operation(summary = "Mark a job application as viewed by the employer", description = "Marks a job application as viewed by the employer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job application marked as viewed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job application not found")
    })
    @PutMapping("/{id}/employer-viewed")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobApplicationResponse> markViewedByEmployer(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Marking job application as viewed by employer: id={}", id);
        String employerId = jwt.getSubject();
        JobApplicationResponse response = jobApplicationService.markViewedByEmployer(id, employerId);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark a job application as viewed by the applicant.
     *
     * @param id  the job application ID
     * @param jwt the JWT token
     * @return the updated job application
     */
    @Operation(summary = "Mark a job application as viewed by the applicant", description = "Marks a job application as viewed by the applicant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job application marked as viewed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job application not found")
    })
    @PutMapping("/{id}/applicant-viewed")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<JobApplicationResponse> markViewedByApplicant(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Marking job application as viewed by applicant: id={}", id);
        String applicantId = jwt.getSubject();
        JobApplicationResponse response = jobApplicationService.markViewedByApplicant(id, applicantId);
        return ResponseEntity.ok(response);
    }

    /**
     * Withdraw a job application.
     *
     * @param id  the job application ID
     * @param jwt the JWT token
     * @return the updated job application
     */
    @Operation(summary = "Withdraw a job application", description = "Withdraws a job application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job application withdrawn successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobApplicationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Job application not found")
    })
    @PutMapping("/{id}/withdraw")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<JobApplicationResponse> withdrawJobApplication(
            @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Withdrawing job application: id={}", id);
        String applicantId = jwt.getSubject();
        JobApplicationResponse response = jobApplicationService.withdrawJobApplication(id, applicantId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all unviewed job applications for the authenticated employer.
     *
     * @param page the page number
     * @param size the page size
     * @param jwt  the JWT token
     * @return a page of job applications
     */
    @Operation(summary = "Get all unviewed job applications for the authenticated employer", description = "Returns a paginated list of unviewed job applications for the authenticated employer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job applications",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/employer-unviewed")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<PageResponse<JobApplicationResponse>> getUnviewedEmployerJobApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting unviewed job applications for authenticated employer: page={}, size={}", page, size);
        String employerId = jwt.getSubject();
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<JobApplicationResponse> response = jobApplicationService.getUnviewedJobApplicationsByEmployerId(employerId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all unviewed job applications for the authenticated applicant.
     *
     * @param page the page number
     * @param size the page size
     * @param jwt  the JWT token
     * @return a page of job applications
     */
    @Operation(summary = "Get all unviewed job applications for the authenticated applicant", description = "Returns a paginated list of unviewed job applications for the authenticated applicant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job applications",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/applicant-unviewed")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<PageResponse<JobApplicationResponse>> getUnviewedApplicantJobApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        log.info("Getting unviewed job applications for authenticated applicant: page={}, size={}", page, size);
        String applicantId = jwt.getSubject();
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<JobApplicationResponse> response = jobApplicationService.getUnviewedJobApplicationsByApplicantId(applicantId, pageable);
        return ResponseEntity.ok(response);
    }
} 