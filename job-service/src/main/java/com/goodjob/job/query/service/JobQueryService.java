package com.goodjob.job.query.service;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.job.query.dto.JobQuery;
import com.goodjob.job.query.dto.JobView;

/**
 * Service interface for job query operations.
 */
public interface JobQueryService {

    PageResponseDTO<JobView> getAllJobs(JobQuery query);

    JobView getJobById(Long id);
}