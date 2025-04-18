package com.goodjob.job.domain.job.repository;

import com.goodjob.job.domain.job.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Job entity.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  @EntityGraph(attributePaths = {"jobSalary", "jobSkills", "jobIndustries", "jobBenefits"})
  Page<JobSummary> findByDeleteFlg(boolean deleteFlg, Pageable pageable);
}