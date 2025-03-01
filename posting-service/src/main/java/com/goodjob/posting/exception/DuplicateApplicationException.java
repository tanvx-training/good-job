package com.goodjob.posting.exception;

/**
 * Exception thrown when a user tries to apply for a job posting more than once.
 */
public class DuplicateApplicationException extends RuntimeException {

    public DuplicateApplicationException(String message) {
        super(message);
    }

    public DuplicateApplicationException(Integer postingId, String applicantId) {
        super("Applicant with ID " + applicantId + " has already applied for job posting with ID " + postingId);
    }
} 