package com.goodjob.posting.service;

import com.goodjob.posting.dto.JobPostingRequest;
import com.goodjob.posting.dto.JobPostingResponse;
import com.goodjob.posting.dto.PageResponse;
import com.goodjob.posting.entity.PostingStatus;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for job posting operations.
 */
public interface JobPostingService {

    /**
     * Create a new job posting.
     *
     * @param request    the job posting request
     * @param employerId the employer ID
     * @return the created job posting
     */
    JobPostingResponse createJobPosting(JobPostingRequest request, String employerId);

    /**
     * Get a job posting by ID.
     *
     * @param id the job posting ID
     * @return the job posting
     */
    JobPostingResponse getJobPostingById(Integer id);

    /**
     * Get all job postings with pagination.
     *
     * @param pageable the pagination information
     * @return a page of job postings
     */
    PageResponse<JobPostingResponse> getAllJobPostings(Pageable pageable);

    /**
     * Get all job postings by employer ID with pagination.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of job postings
     */
    PageResponse<JobPostingResponse> getJobPostingsByEmployerId(String employerId, Pageable pageable);

    /**
     * Get all job postings by status with pagination.
     *
     * @param status   the job posting status
     * @param pageable the pagination information
     * @return a page of job postings
     */
    PageResponse<JobPostingResponse> getJobPostingsByStatus(PostingStatus status, Pageable pageable);

    /**
     * Update a job posting.
     *
     * @param id         the job posting ID
     * @param request    the job posting request
     * @param employerId the employer ID
     * @return the updated job posting
     */
    JobPostingResponse updateJobPosting(Integer id, JobPostingRequest request, String employerId);

    /**
     * Delete a job posting.
     *
     * @param id         the job posting ID
     * @param employerId the employer ID
     */
    void deleteJobPosting(Integer id, String employerId);

    /**
     * Search for job postings by keyword with pagination.
     *
     * @param keyword  the search keyword
     * @param pageable the pagination information
     * @return a page of job postings
     */
    PageResponse<JobPostingResponse> searchJobPostings(String keyword, Pageable pageable);

    /**
     * Increment the view count of a job posting.
     *
     * @param id the job posting ID
     * @return the new view count
     */
    Integer incrementViewCount(Integer id);

    /**
     * Update the status of a job posting.
     *
     * @param id         the job posting ID
     * @param status     the new status
     * @param employerId the employer ID
     * @return the updated job posting
     */
    JobPostingResponse updateJobPostingStatus(Integer id, PostingStatus status, String employerId);
} 