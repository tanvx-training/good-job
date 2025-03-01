package com.goodjob.posting.repository;

import com.goodjob.posting.entity.JobPosting;
import com.goodjob.posting.entity.PostingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for JobPosting entity.
 */
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer>, JpaSpecificationExecutor<JobPosting> {

    /**
     * Find all job postings by employer ID.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByEmployerId(String employerId, Pageable pageable);

    /**
     * Find all job postings by status.
     *
     * @param status   the job posting status
     * @param pageable the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByStatus(PostingStatus status, Pageable pageable);

    /**
     * Find all job postings by employer ID and status.
     *
     * @param employerId the employer ID
     * @param status     the job posting status
     * @param pageable   the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByEmployerIdAndStatus(String employerId, PostingStatus status, Pageable pageable);

    /**
     * Find all job postings that expire before a given date.
     *
     * @param expiresAt the expiration date
     * @return a list of job postings
     */
    List<JobPosting> findByExpiresAtBeforeAndStatus(LocalDateTime expiresAt, PostingStatus status);

    /**
     * Find a job posting by ID and employer ID.
     *
     * @param id         the job posting ID
     * @param employerId the employer ID
     * @return an optional job posting
     */
    Optional<JobPosting> findByIdAndEmployerId(Integer id, String employerId);

    /**
     * Find all job postings by job ID.
     *
     * @param jobId    the job ID
     * @param pageable the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByJobId(Integer jobId, Pageable pageable);

    /**
     * Find all job postings by location.
     *
     * @param location the job location
     * @param status   the job posting status
     * @param pageable the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByLocationContainingIgnoreCaseAndStatus(String location, PostingStatus status, Pageable pageable);

    /**
     * Find all job postings by company name.
     *
     * @param companyName the company name
     * @param status      the job posting status
     * @param pageable    the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByCompanyNameContainingIgnoreCaseAndStatus(String companyName, PostingStatus status, Pageable pageable);

    /**
     * Find all job postings by title.
     *
     * @param title    the job title
     * @param status   the job posting status
     * @param pageable the pagination information
     * @return a page of job postings
     */
    Page<JobPosting> findByTitleContainingIgnoreCaseAndStatus(String title, PostingStatus status, Pageable pageable);

    /**
     * Search for job postings by keyword in title, description, or company name.
     *
     * @param keyword  the search keyword
     * @param status   the job posting status
     * @param pageable the pagination information
     * @return a page of job postings
     */
    @Query("SELECT jp FROM JobPosting jp WHERE " +
            "(LOWER(jp.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(jp.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(jp.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND jp.status = :status")
    Page<JobPosting> searchByKeyword(@Param("keyword") String keyword, @Param("status") PostingStatus status, Pageable pageable);
} 