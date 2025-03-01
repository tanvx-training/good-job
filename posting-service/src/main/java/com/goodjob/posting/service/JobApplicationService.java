package com.goodjob.posting.service;

import com.goodjob.posting.dto.JobApplicationRequest;
import com.goodjob.posting.dto.JobApplicationResponse;
import com.goodjob.posting.dto.PageResponse;
import com.goodjob.posting.entity.ApplicationStatus;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for job application operations.
 */
public interface JobApplicationService {

    /**
     * Apply for a job posting.
     *
     * @param postingId   the job posting ID
     * @param request     the job application request
     * @param applicantId the applicant ID
     * @return the created job application
     */
    JobApplicationResponse applyForJob(Integer postingId, JobApplicationRequest request, String applicantId);

    /**
     * Get a job application by ID.
     *
     * @param id the job application ID
     * @return the job application
     */
    JobApplicationResponse getJobApplicationById(Integer id);

    /**
     * Get all job applications for a job posting with pagination.
     *
     * @param postingId the job posting ID
     * @param pageable  the pagination information
     * @return a page of job applications
     */
    PageResponse<JobApplicationResponse> getJobApplicationsByPostingId(Integer postingId, Pageable pageable);

    /**
     * Get all job applications by applicant ID with pagination.
     *
     * @param applicantId the applicant ID
     * @param pageable    the pagination information
     * @return a page of job applications
     */
    PageResponse<JobApplicationResponse> getJobApplicationsByApplicantId(String applicantId, Pageable pageable);

    /**
     * Get all job applications by employer ID with pagination.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of job applications
     */
    PageResponse<JobApplicationResponse> getJobApplicationsByEmployerId(String employerId, Pageable pageable);

    /**
     * Update the status of a job application.
     *
     * @param id         the job application ID
     * @param status     the new status
     * @param employerId the employer ID
     * @return the updated job application
     */
    JobApplicationResponse updateJobApplicationStatus(Integer id, ApplicationStatus status, String employerId);

    /**
     * Mark a job application as viewed by the employer.
     *
     * @param id         the job application ID
     * @param employerId the employer ID
     * @return the updated job application
     */
    JobApplicationResponse markViewedByEmployer(Integer id, String employerId);

    /**
     * Mark a job application as viewed by the applicant.
     *
     * @param id          the job application ID
     * @param applicantId the applicant ID
     * @return the updated job application
     */
    JobApplicationResponse markViewedByApplicant(Integer id, String applicantId);

    /**
     * Withdraw a job application.
     *
     * @param id          the job application ID
     * @param applicantId the applicant ID
     * @return the updated job application
     */
    JobApplicationResponse withdrawJobApplication(Integer id, String applicantId);

    /**
     * Get all unviewed job applications by employer ID.
     *
     * @param employerId the employer ID
     * @return a list of job applications
     */
    PageResponse<JobApplicationResponse> getUnviewedJobApplicationsByEmployerId(String employerId, Pageable pageable);

    /**
     * Get all unviewed job applications by applicant ID.
     *
     * @param applicantId the applicant ID
     * @return a list of job applications
     */
    PageResponse<JobApplicationResponse> getUnviewedJobApplicationsByApplicantId(String applicantId, Pageable pageable);
} 