package com.goodjob.posting.repository;

import com.goodjob.posting.entity.ApplicationStatus;
import com.goodjob.posting.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for JobApplication entity.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    /**
     * Find all applications by posting ID.
     *
     * @param postingId the posting ID
     * @param pageable  the pagination information
     * @return a page of job applications
     */
    Page<JobApplication> findByJobPostingId(Integer postingId, Pageable pageable);

    /**
     * Find all applications by applicant ID.
     *
     * @param applicantId the applicant ID
     * @param pageable    the pagination information
     * @return a page of job applications
     */
    Page<JobApplication> findByApplicantId(String applicantId, Pageable pageable);

    /**
     * Find all applications by posting ID and status.
     *
     * @param postingId the posting ID
     * @param status    the application status
     * @param pageable  the pagination information
     * @return a page of job applications
     */
    Page<JobApplication> findByJobPostingIdAndStatus(Integer postingId, ApplicationStatus status, Pageable pageable);

    /**
     * Find all applications by applicant ID and status.
     *
     * @param applicantId the applicant ID
     * @param status      the application status
     * @param pageable    the pagination information
     * @return a page of job applications
     */
    Page<JobApplication> findByApplicantIdAndStatus(String applicantId, ApplicationStatus status, Pageable pageable);

    /**
     * Find an application by posting ID and applicant ID.
     *
     * @param postingId   the posting ID
     * @param applicantId the applicant ID
     * @return an optional job application
     */
    Optional<JobApplication> findByJobPostingIdAndApplicantId(Integer postingId, String applicantId);

    /**
     * Find all applications by employer ID.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of job applications
     */
    @Query("SELECT ja FROM JobApplication ja JOIN ja.jobPosting jp WHERE jp.employerId = :employerId")
    Page<JobApplication> findByEmployerId(@Param("employerId") String employerId, Pageable pageable);

    /**
     * Find all applications by employer ID and status.
     *
     * @param employerId the employer ID
     * @param status     the application status
     * @param pageable   the pagination information
     * @return a page of job applications
     */
    @Query("SELECT ja FROM JobApplication ja JOIN ja.jobPosting jp WHERE jp.employerId = :employerId AND ja.status = :status")
    Page<JobApplication> findByEmployerIdAndStatus(@Param("employerId") String employerId, @Param("status") ApplicationStatus status, Pageable pageable);

    /**
     * Find all applications by employer ID and posting ID.
     *
     * @param employerId the employer ID
     * @param postingId  the posting ID
     * @param pageable   the pagination information
     * @return a page of job applications
     */
    @Query("SELECT ja FROM JobApplication ja JOIN ja.jobPosting jp WHERE jp.employerId = :employerId AND jp.id = :postingId")
    Page<JobApplication> findByEmployerIdAndPostingId(@Param("employerId") String employerId, @Param("postingId") Integer postingId, Pageable pageable);

    /**
     * Count the number of applications by posting ID.
     *
     * @param postingId the posting ID
     * @return the number of applications
     */
    long countByJobPostingId(Integer postingId);

    /**
     * Count the number of applications by posting ID and status.
     *
     * @param postingId the posting ID
     * @param status    the application status
     * @return the number of applications
     */
    long countByJobPostingIdAndStatus(Integer postingId, ApplicationStatus status);

    /**
     * Find all unviewed applications by employer ID.
     *
     * @param employerId the employer ID
     * @return a list of job applications
     */
    @Query("SELECT ja FROM JobApplication ja JOIN ja.jobPosting jp WHERE jp.employerId = :employerId AND ja.employerViewed = false")
    List<JobApplication> findUnviewedByEmployerId(@Param("employerId") String employerId);

    /**
     * Find all unviewed applications by applicant ID.
     *
     * @param applicantId the applicant ID
     * @return a list of job applications
     */
    List<JobApplication> findByApplicantIdAndApplicantViewedFalse(String applicantId);
} 