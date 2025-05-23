package com.goodjob.job.api.rest;

import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.job.domain.job.dto.PostJobCommand;
import com.goodjob.job.domain.job.command.JobCommandService;
import com.goodjob.job.domain.job.dto.JobQuery;
import com.goodjob.job.domain.job.dto.JobView;
import com.goodjob.job.domain.job.query.JobQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing job postings.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobQueryService jobQueryService;

  private final JobCommandService jobCommandService;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_JOB')")
    public ResponseEntity<ApiResponse<JobView>> getJobById(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(jobQueryService.getJobById(id)));
    }

    @PostMapping("/{id}/posting")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('POST_JOB')")
    public void postJob(@PathVariable("id") Long id, @RequestBody PostJobCommand command) {
        jobCommandService.postJob(id, command);
    }
} 