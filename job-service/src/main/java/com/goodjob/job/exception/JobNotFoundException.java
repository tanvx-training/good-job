package com.goodjob.job.exception;

/**
 * Exception thrown when a job is not found.
 */
public class JobNotFoundException extends RuntimeException {

    /**
     * Constructs a new JobNotFoundException with the specified job ID.
     *
     * @param id the job ID
     */
    public JobNotFoundException(Long id) {
        super("Job not found with ID: " + id);
    }
} 