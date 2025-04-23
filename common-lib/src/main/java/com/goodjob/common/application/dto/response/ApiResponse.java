package com.goodjob.common.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Standard API response wrapper for all REST endpoints.
 * Provides a consistent response structure across all services.
 *
 * @param <T> the type of data in the response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    private boolean success;
    private String message;
    private T data;
    private Object errors;

    /**
     * Creates a successful response with data.
     *
     * @param data the response data
     * @param message the success message
     * @param <T> the type of data
     * @return a successful ApiResponse
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates a successful response with data and default message.
     *
     * @param data the response data
     * @param <T> the type of data
     * @return a successful ApiResponse
     */
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operation successful");
    }

    /**
     * Creates a successful response with just a message.
     *
     * @param message the success message
     * @param <T> the type of data
     * @return a successful ApiResponse
     */
    public static <T> ApiResponse<T> success(String message) {
        return success(null, message);
    }

    /**
     * Creates an error response.
     *
     * @param message the error message
     * @param errors detailed error information
     * @param <T> the type of data
     * @return an error ApiResponse
     */
    public static <T> ApiResponse<T> error(String message, Object errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }

    /**
     * Creates an error response.
     *
     * @param errors detailed error information
     * @param <T> the type of data
     * @return an error ApiResponse
     */
    public static <T> ApiResponse<T> error(Object errors) {
        return ApiResponse.<T>builder()
            .success(false)
            .message("Operation failed")
            .errors(errors)
            .build();
    }

    /**
     * Creates an error response with just a message.
     *
     * @param message the error message
     * @param <T> the type of data
     * @return an error ApiResponse
     */
    public static <T> ApiResponse<T> error(String message) {
        return error(message, null);
    }
} 