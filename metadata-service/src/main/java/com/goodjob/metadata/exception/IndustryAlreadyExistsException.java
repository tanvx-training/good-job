package com.goodjob.metadata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an industry already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class IndustryAlreadyExistsException extends RuntimeException {

    public IndustryAlreadyExistsException(String message) {
        super(message);
    }
} 