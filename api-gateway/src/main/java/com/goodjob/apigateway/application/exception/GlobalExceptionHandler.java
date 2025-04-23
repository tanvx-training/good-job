package com.goodjob.apigateway.application.exception;

import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.exception.BadRequestException;
import com.goodjob.common.application.exception.ResourceExistedException;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error("Validation Error")
        .message("Input validation failed")
        .details(errors)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleBadCredentialsException(
      BadCredentialsException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.UNAUTHORIZED.value())
        .error("Authentication Error")
        .message(ex.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleUsernameNotFoundException(
      UsernameNotFoundException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error("User Not Found")
        .message(ex.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleAccessDeniedException(
      AccessDeniedException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.FORBIDDEN.value())
        .error("Access Denied")
        .message("You don't have permission to access this resource")
        .build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(ResourceExistedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
      ResourceExistedException ex) {
    log.error("Resource existed: {}", ex.getMessage());

    Map<String, Object> details = new HashMap<>();
    details.put("resourceName", ex.getResourceName());
    details.put("fieldName", ex.getFieldName());
    details.put("fieldValue", ex.getFieldValue());

    ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  /**
   * Handles BadRequestException.
   *
   * @param ex the exception
   * @return a ResponseEntity with the error details
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
    log.error("Bad request: {}", ex.getMessage());

    ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * Handles ResourceNotFoundException.
   *
   * @param ex the exception
   * @return a ResponseEntity with the error details
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    log.error("Resource not found: {}", ex.getMessage());

    Map<String, Object> details = new HashMap<>();
    details.put("resourceName", ex.getResourceName());
    details.put("fieldName", ex.getFieldName());
    details.put("fieldValue", ex.getFieldValue());

    ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleGlobalException(Exception ex) {
    log.error("Unhandled exception: ", ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .error("Internal Server Error")
        .message("An unexpected error occurred")
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(errorResponse));
  }
}