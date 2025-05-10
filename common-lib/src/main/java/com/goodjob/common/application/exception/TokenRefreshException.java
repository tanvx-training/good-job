package com.goodjob.common.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a refresh token is invalid or expired.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String token;

  public TokenRefreshException(String token, String message) {
    super(message);
    this.token = token;
  }

  public String getToken() {
    return token;
  }
} 