package com.goodjob.notification.exception;

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

    /**
     * Constructs a new UnauthorizedAccessException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
} 