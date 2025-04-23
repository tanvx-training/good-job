package com.goodjob.job.domain.job.query;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.job.domain.job.dto.JobQuery;
import com.goodjob.job.domain.job.dto.JobView;

/**
 * Service interface for job query operations.
 */
public interface JobQueryService {

    PageResponseDTO<JobView> getAllJobs(JobQuery query);

    JobView getJobById(Long id);
}