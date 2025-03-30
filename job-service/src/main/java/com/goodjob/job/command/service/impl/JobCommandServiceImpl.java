package com.goodjob.job.command.service.impl;

import com.goodjob.job.command.dto.PostJobCommand;
import com.goodjob.job.command.event.JobPostingEvent;
import com.goodjob.job.command.event.JobPostingProducer;
import com.goodjob.job.command.service.JobCommandService;
import com.goodjob.job.common.enums.JobStatus;
import com.goodjob.job.common.enums.PostingType;
import com.goodjob.job.entity.Job;
import com.goodjob.job.exception.JobNotFoundException;
import com.goodjob.job.repository.JobRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the JobCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobCommandServiceImpl implements JobCommandService {

  private final JobRepository jobRepository;

  private final JobPostingProducer jobPostingProducer;

  @Override
  public void postJob(Long id, PostJobCommand command) {

    Job job = jobRepository.findById(id)
        .filter(o -> Objects.equals(o.getJobStatus(), JobStatus.OPEN.getCode()))
        .orElseThrow(() -> new JobNotFoundException(id));
    PostingType postingType = PostingType.fromValue(command.getPostingCode());

    switch (postingType) {
      case POSTING -> jobPostingProducer.postJob(JobPostingEvent.builder()
          .jobId(job.getJobId())
          .build());
      case UN_POSTING -> jobPostingProducer.unPostJob(JobPostingEvent.builder()
          .jobId(job.getJobId())
          .build());
    }
  }
}