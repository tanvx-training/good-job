package com.goodjob.job.application.helper;

import com.goodjob.job.domain.job.repository.JobSummary;

public interface PostingHelper {

    String generateJobDescription(JobSummary job);
}
