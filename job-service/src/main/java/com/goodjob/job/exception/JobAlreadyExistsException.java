package com.goodjob.job.exception;

/**
 * Exception thrown when a job already exists.
 */
public class JobAlreadyExistsException extends RuntimeException {

    public JobAlreadyExistsException(String message) {
        super(message);
    }

    public JobAlreadyExistsException(Integer id) {
        super("Job already exists with id: " + id);
    }
} 