package com.goodjob.metadata.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Benefit Service.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle validation exceptions.
   *
   * @param ex the MethodArgumentNotValidException
   * @return the error response
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> response = new HashMap<>();
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Validation Error");
    response.put("details", errors);

    return response;
  }

  /**
   * Handle BenefitNotFoundException.
   *
   * @param ex      the BenefitNotFoundException
   * @param request the WebRequest
   * @return the error response
   */
  @ExceptionHandler(BenefitNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBenefitNotFoundException(BenefitNotFoundException ex,
      WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .timestamp(LocalDateTime.now())
        .error(ex.getMessage())
        .message(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handle BenefitAlreadyExistsException.
   *
   * @param ex      the BenefitAlreadyExistsException
   * @param request the WebRequest
   * @return the error response
   */
  @ExceptionHandler(BenefitAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleBenefitAlreadyExistsException(
      BenefitAlreadyExistsException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.CONFLICT.value())
        .timestamp(LocalDateTime.now())
        .error(ex.getMessage())
        .message(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  /**
   * Handles SkillNotFoundException.
   *
   * @param ex the exception
   * @param request the web request
   * @return the error response
   */
  @ExceptionHandler(IndustryNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleSkillNotFoundException(IndustryNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .error(ex.getMessage())
            .message(request.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles SkillAlreadyExistsException.
   *
   * @param ex the exception
   * @param request the web request
   * @return the error response
   */
  @ExceptionHandler(IndustryAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleSkillAlreadyExistsException(IndustryAlreadyExistsException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.CONFLICT.value())
            .timestamp(LocalDateTime.now())
            .error(ex.getMessage())
            .message(request.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  /**
   * Handles SkillNotFoundException.
   *
   * @param ex the exception
   * @param request the web request
   * @return the error response
   */
  @ExceptionHandler(SkillNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleSkillNotFoundException(SkillNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .error(ex.getMessage())
            .message(request.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles SkillAlreadyExistsException.
   *
   * @param ex the exception
   * @param request the web request
   * @return the error response
   */
  @ExceptionHandler(SkillAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleSkillAlreadyExistsException(SkillAlreadyExistsException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.CONFLICT.value())
            .timestamp(LocalDateTime.now())
            .error(ex.getMessage())
            .message(request.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  /**
   * Handle AccessDeniedException.
   *
   * @param ex      the AccessDeniedException
   * @param request the WebRequest
   * @return the error response
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
      WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.FORBIDDEN.value())
        .timestamp(LocalDateTime.now())
        .error("Access denied: " + ex.getMessage())
        .message(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  /**
   * Handle general exceptions.
   *
   * @param ex      the Exception
   * @param request the WebRequest
   * @return the error response
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .timestamp(LocalDateTime.now())
        .error(ex.getMessage())
        .message(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  /**
   * Error response class for structured error responses.
   */
  @Data
  @Builder
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
  }
} 