package com.goodjob.job.command.service;

import com.goodjob.job.command.dto.CreateJobCommand;
import com.goodjob.job.command.dto.UpdateJobCommand;
import com.goodjob.job.entity.JobStatus;

/**
 * Service interface for job command operations.
 */
public interface JobCommandService {

    /**
     * Create a new job posting.
     *
     * @param command    the job creation command
     * @param employerId the ID of the employer creating the job
     * @return the ID of the created job
     */
    Integer createJob(CreateJobCommand command, String employerId);

    /**
     * Update an existing job posting.
     *
     * @param id         the ID of the job to update
     * @param command    the job update command
     * @param employerId the ID of the employer updating the job
     * @return the ID of the updated job
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     * @throws com.goodjob.job.exception.UnauthorizedAccessException if the employer is not authorized to update the job
     */
    Integer updateJob(Integer id, UpdateJobCommand command, String employerId);

    /**
     * Delete a job posting.
     *
     * @param id         the ID of the job to delete
     * @param employerId the ID of the employer deleting the job
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     * @throws com.goodjob.job.exception.UnauthorizedAccessException if the employer is not authorized to delete the job
     */
    void deleteJob(Integer id, String employerId);

    /**
     * Update the status of a job posting.
     *
     * @param id         the ID of the job to update
     * @param status     the new status
     * @param employerId the ID of the employer updating the job
     * @return the ID of the updated job
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     * @throws com.goodjob.job.exception.UnauthorizedAccessException if the employer is not authorized to update the job
     */
    Integer updateJobStatus(Integer id, JobStatus status, String employerId);

    /**
     * Increment the view count of a job posting.
     *
     * @param id the ID of the job
     * @return the new view count
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     */
    Integer incrementJobViews(Integer id);

    /**
     * Increment the application count of a job posting.
     *
     * @param id the ID of the job
     * @return the new application count
     * @throws com.goodjob.job.exception.JobNotFoundException if the job is not found
     */
    Integer incrementJobApplications(Integer id);
} 