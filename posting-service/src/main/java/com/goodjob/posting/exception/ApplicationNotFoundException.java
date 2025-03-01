package com.goodjob.posting.exception;

/**
 * Exception thrown when a job application is not found.
 */
public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(String message) {
        super(message);
    }

    public ApplicationNotFoundException(Integer id) {
        super("Job application not found with id: " + id);
    }
} 