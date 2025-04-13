package com.goodjob.metadata.application.exception;

/**
 * Exception thrown when a speciality already exists.
 */
public class SpecialityAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new SpecialityAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public SpecialityAlreadyExistsException(String message) {
        super(message);
    }
} 