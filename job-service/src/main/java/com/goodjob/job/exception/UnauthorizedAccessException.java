package com.goodjob.job.exception;

/**
 * Exception thrown when a user attempts to access a resource they are not authorized to access.
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     * Constructs a new UnauthorizedAccessException with the specified message.
     *
     * @param message the detail message
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException() {
        super("You are not authorized to access this resource");
    }
} 