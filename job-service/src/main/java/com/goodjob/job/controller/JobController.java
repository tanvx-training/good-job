package com.goodjob.job.controller;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.job.query.dto.JobQuery;
import com.goodjob.job.query.dto.JobView;
import com.goodjob.job.query.service.JobQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing job postings.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobQueryService jobQueryService;

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_JOB')")
  public ResponseEntity<ApiResponse<PageResponseDTO<JobView>>> getAllJobs(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "20") Integer size,
          @RequestParam(value = "sort", defaultValue = "jobId,asc") String sort
  ) {
      return ResponseEntity.ok(ApiResponse.success(jobQueryService.getAllJobs(
              JobQuery
                  .builder()
                  .page(page)
                  .size(size)
                  .sort(sort)
                  .build())
      ));
  }
} 