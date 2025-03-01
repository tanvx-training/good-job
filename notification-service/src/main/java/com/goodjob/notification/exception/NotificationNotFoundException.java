package com.goodjob.notification.exception;

/**
 * Exception thrown when a notification is not found.
 */
public class NotificationNotFoundException extends RuntimeException {

    /**
     * Constructs a new NotificationNotFoundException with the specified message.
     *
     * @param message the detail message
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotificationNotFoundException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 