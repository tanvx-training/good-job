package com.goodjob.candidate.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a structured error response for REST API.
 */
@Getter
@Setter
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private List<FieldError> errors;

    /**
     * Constructs a new ErrorResponse with the specified status and message.
     *
     * @param status the HTTP status
     * @param message the error message
     */
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    /**
     * Constructs a new ErrorResponse with the specified status, message, and field errors.
     *
     * @param status the HTTP status
     * @param message the error message
     * @param errors the list of field errors
     */
    public ErrorResponse(HttpStatus status, String message, List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    /**
     * Adds a validation error to the list of errors.
     *
     * @param field the field name
     * @param message the error message
     */
    public void addValidationError(String field, String message) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new FieldError(field, message));
    }

    /**
     * Class representing a field-specific validation error.
     */
    @Getter
    @Setter
    public static class FieldError {
        private String field;
        private String message;

        /**
         * Constructs a new FieldError with the specified field and message.
         *
         * @param field the field name
         * @param message the error message
         */
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
} 