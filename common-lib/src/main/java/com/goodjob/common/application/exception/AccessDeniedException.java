package com.goodjob.common.application.exception;

/**
 * Exception thrown when a user attempts to access a resource they don't have permission for.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with the specified detail message.
     *
     * @param message the detail message
     */
    public AccessDeniedException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccessDeniedException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
} 