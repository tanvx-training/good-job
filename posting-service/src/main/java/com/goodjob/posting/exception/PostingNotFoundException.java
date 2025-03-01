package com.goodjob.posting.exception;

/**
 * Exception thrown when a job posting is not found.
 */
public class PostingNotFoundException extends RuntimeException {

    public PostingNotFoundException(String message) {
        super(message);
    }

    public PostingNotFoundException(Integer id) {
        super("Job posting not found with id: " + id);
    }
} 