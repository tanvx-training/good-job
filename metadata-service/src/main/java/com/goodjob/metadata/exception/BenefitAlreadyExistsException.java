package com.goodjob.metadata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to create a benefit that already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BenefitAlreadyExistsException extends RuntimeException {

    public BenefitAlreadyExistsException(String message) {
        super(message);
    }
} 