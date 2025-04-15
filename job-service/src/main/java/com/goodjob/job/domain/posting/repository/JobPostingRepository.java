package com.goodjob.job.domain.posting.repository;

import com.goodjob.job.domain.posting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for JobPosting entity.
 */
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

} 