package com.goodjob.job.repository;

import com.goodjob.job.entity.Job;
import com.goodjob.job.entity.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Job entity.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {

    /**
     * Find all jobs by employer ID.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of jobs
     */
    Page<Job> findByEmployerId(String employerId, Pageable pageable);

    /**
     * Find all jobs by status.
     *
     * @param status   the job status
     * @param pageable the pagination information
     * @return a page of jobs
     */
    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    /**
     * Find all jobs by employer ID and status.
     *
     * @param employerId the employer ID
     * @param status     the job status
     * @param pageable   the pagination information
     * @return a page of jobs
     */
    Page<Job> findByEmployerIdAndStatus(String employerId, JobStatus status, Pageable pageable);

    /**
     * Find all jobs that expire before a given date.
     *
     * @param expiresAt the expiration date
     * @return a list of jobs
     */
    List<Job> findByExpiresAtBefore(LocalDateTime expiresAt);

    /**
     * Find a job by ID and employer ID.
     *
     * @param id         the job ID
     * @param employerId the employer ID
     * @return an optional job
     */
    Optional<Job> findByIdAndEmployerId(Integer id, String employerId);

    /**
     * Search for jobs using the JSONB search vector.
     *
     * @param query    the search query
     * @param pageable the pagination information
     * @return a page of jobs
     */
    @Query(value = "SELECT j.* FROM jobs j WHERE j.search_vector @> CAST(:query AS jsonb) AND j.status = 'ACTIVE'", 
           nativeQuery = true)
    Page<Job> searchJobs(@Param("query") String query, Pageable pageable);

    /**
     * Find all jobs by location.
     *
     * @param location the job location
     * @param pageable the pagination information
     * @return a page of jobs
     */
    Page<Job> findByLocationContainingIgnoreCaseAndStatus(String location, JobStatus status, Pageable pageable);

    /**
     * Find all jobs by company name.
     *
     * @param companyName the company name
     * @param pageable    the pagination information
     * @return a page of jobs
     */
    Page<Job> findByCompanyNameContainingIgnoreCaseAndStatus(String companyName, JobStatus status, Pageable pageable);

    /**
     * Find all jobs by title.
     *
     * @param title    the job title
     * @param pageable the pagination information
     * @return a page of jobs
     */
    Page<Job> findByTitleContainingIgnoreCaseAndStatus(String title, JobStatus status, Pageable pageable);

    /**
     * Increment the views count of a job.
     *
     * @param id the job ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE Job j SET j.views = j.views + 1 WHERE j.id = :id")
    int incrementViews(@Param("id") Integer id);

    /**
     * Increment the applications count of a job.
     *
     * @param id the job ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE Job j SET j.applications = j.applications + 1 WHERE j.id = :id")
    int incrementApplications(@Param("id") Integer id);

    /**
     * Update the search vector of a job.
     *
     * @param id           the job ID
     * @param searchVector the search vector
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE Job j SET j.searchVector = :searchVector WHERE j.id = :id")
    int updateSearchVector(@Param("id") Integer id, @Param("searchVector") String searchVector);
} 