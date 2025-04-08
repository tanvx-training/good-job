package com.goodjob.metadata.exception;

/**
 * Exception thrown when a speciality is not found.
 */
public class SpecialityNotFoundException extends RuntimeException {

    /**
     * Constructs a new SpecialityNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public SpecialityNotFoundException(String message) {
        super(message);
    }
} 