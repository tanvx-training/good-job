package com.goodjob.job.command.service;

import com.goodjob.job.command.dto.PostJobCommand;

/**
 * Service interface for job command operations.
 */
public interface JobCommandService {

  void postJob(Long id, PostJobCommand command);
}