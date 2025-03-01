package com.goodjob.candidate.exception;

/**
 * Exception thrown when a candidate is not found.
 */
public class CandidateNotFoundException extends RuntimeException {

    /**
     * Constructs a new CandidateNotFoundException with the specified ID.
     *
     * @param id the ID of the candidate that was not found
     */
    public CandidateNotFoundException(Integer id) {
        super("Candidate not found with ID: " + id);
    }

    /**
     * Constructs a new CandidateNotFoundException with the specified user ID.
     *
     * @param userId the user ID of the candidate that was not found
     */
    public CandidateNotFoundException(String userId) {
        super("Candidate not found with user ID: " + userId);
    }
} 