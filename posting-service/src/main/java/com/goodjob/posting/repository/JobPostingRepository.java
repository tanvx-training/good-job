package com.goodjob.posting.repository;

import com.goodjob.posting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for JobPosting entity.
 */
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

} 