package com.goodjob.metadata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an industry is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IndustryNotFoundException extends RuntimeException {

    public IndustryNotFoundException(String message) {
        super(message);
    }
} 