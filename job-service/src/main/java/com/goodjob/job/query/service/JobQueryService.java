package com.goodjob.job.query.service;

import com.goodjob.job.entity.JobStatus;
import com.goodjob.job.query.dto.JobSearchCriteria;
import com.goodjob.job.query.dto.JobView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for job query operations.
 */
public interface JobQueryService {

    /**
     * Get a job by ID.
     *
     * @param id the ID of the job
     * @return the job details
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     */
    JobView getJobById(Integer id);

    /**
     * Get jobs by employer ID.
     *
     * @param employerId the ID of the employer
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of jobs
     */
    Page<JobView> getJobsByEmployerId(String employerId, int page, int size, String sort, String direction);

    /**
     * Get jobs by status.
     *
     * @param status     the job status
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of jobs
     */
    Page<JobView> getJobsByStatus(JobStatus status, int page, int size, String sort, String direction);

    /**
     * Get jobs by employer ID and status.
     *
     * @param employerId the ID of the employer
     * @param status     the job status
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of jobs
     */
    Page<JobView> getJobsByEmployerIdAndStatus(String employerId, JobStatus status, int page, int size, String sort, String direction);

    /**
     * Search for jobs using a text query.
     *
     * @param query      the search query
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of jobs matching the search query
     */
    Page<JobView> searchJobs(String query, int page, int size, String sort, String direction);

    /**
     * Search for jobs using criteria.
     *
     * @param criteria   the search criteria
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of jobs matching the search criteria
     */
    Page<JobView> searchJobsWithCriteria(JobSearchCriteria criteria, int page, int size, String sort, String direction);

    /**
     * Get expired jobs.
     *
     * @return a list of expired jobs
     */
    List<JobView> getExpiredJobs();
} 