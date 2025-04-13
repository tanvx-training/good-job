package com.goodjob.job.domain.job.command;

import com.goodjob.job.domain.job.dto.PostJobCommand;

/**
 * Service interface for job command operations.
 */
public interface JobCommandService {

  void postJob(Long id, PostJobCommand command);
}