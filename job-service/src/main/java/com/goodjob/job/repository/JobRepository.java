package com.goodjob.job.repository;

import com.goodjob.job.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Job entity.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  @EntityGraph(attributePaths = {"jobSalary", "jobSkills", "jobIndustries", "jobBenefits"})
  Page<JobSummary> findByDeleteFlg(boolean deleteFlg, Pageable pageable);
} 