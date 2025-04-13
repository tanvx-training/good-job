package com.goodjob.metadata.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a benefit is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BenefitNotFoundException extends RuntimeException {

    public BenefitNotFoundException(String message) {
        super(message);
    }
} 