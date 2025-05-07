package com.goodjob.job.domain.job.repository;

import com.goodjob.job.domain.job.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Job entity.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  @EntityGraph(attributePaths = {"jobSalary", "jobSkills", "jobIndustries", "jobBenefits"})
  Page<JobSummary> findByDeleteFlg(boolean deleteFlg, Pageable pageable);

  @Query(value = """
        SELECT DISTINCT
            j.job_id,
            j.company_id,
            j.title,
            j.description,
            j.work_type,
            j.education_level,
            j.experience_level,
            j.remote_allowed,
            j.location,
            j.zip_code,
            j.skills_desc,
            j.expiry,
            j.closed_time,
            j.job_status,
            j.views,
            j.applies,
            js.salary_id,
            js.min_salary,
            js.med_salary,
            js.max_salary,
            js.pay_period,
            js.currency,
            js.compensation_type,
            jb.benefit_id,
            jsk.skill_id,
            ji.industry_id
        FROM
            jobs j
        LEFT JOIN 
            job_salaries js ON j.job_id = js.job_id
        LEFT JOIN 
            job_benefits jb ON j.job_id = jb.job_id
        LEFT JOIN 
            job_skills jsk ON j.job_id = jsk.job_id
        LEFT JOIN 
            job_industries ji ON j.job_id = ji.job_id
        WHERE 
            j.delete_flg = :deleteFlg
        ORDER BY 
            j.job_id
        LIMIT :limit OFFSET :offset
        """,
          countQuery = "SELECT COUNT(*) FROM jobs j WHERE j.delete_flg = :deleteFlg",
          nativeQuery = true)
  List<Object[]> findJobSummaries(@Param("deleteFlg") boolean deleteFlg,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);

  long countByDeleteFlg(boolean deleteFlg);
}