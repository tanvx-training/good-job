package com.goodjob.apigateway.service.impl;

import com.goodjob.apigateway.dto.response.LoginResponse;
import com.goodjob.apigateway.dto.request.RegisterRequest;
import com.goodjob.apigateway.dto.response.UserDTO;
import com.goodjob.apigateway.dto.request.LoginRequest;
import com.goodjob.apigateway.entity.RefreshToken;
import com.goodjob.apigateway.entity.User;
import com.goodjob.apigateway.security.jwt.JwtTokenProvider;
import com.goodjob.apigateway.service.AuthService;
import com.goodjob.apigateway.service.RefreshTokenService;
import com.goodjob.apigateway.service.UserService;
import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.exception.ResourceExistedException;
import com.goodjob.common.exception.ResourceNotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final ReactiveAuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;
  private final ReactiveUserDetailsService userDetailsService;
  private final RefreshTokenService refreshTokenService;

  @Override
  public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword()
          )
      ).toFuture().get();

      // Generate JWT access token
      Objects.requireNonNull(authentication);
      String accessToken = jwtTokenProvider.generateAccessToken(authentication);

      // Create refresh token and save to database
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(
          loginRequest.getUsername());

      UserDTO userDTO = userService.getUserDetails(loginRequest.getUsername());

      return ApiResponse.success(LoginResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken.getToken())
          .tokenType("Bearer")
          .expiresIn(jwtTokenProvider.getExpirationTime())
          .user(userDTO)
          .build());
    } catch (Exception e) {
      log.error("Authentication failed: {}", e.getMessage());
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @Override
  @Transactional
  public UserDTO register(RegisterRequest registerRequest) {
    if (userService.existsByUsername(registerRequest.getUsername())) {
      throw new ResourceExistedException(User.class.getName(), "username",
          registerRequest.getUsername());
    }

    return userService.createUser(registerRequest.getUsername(), registerRequest.getPassword());
  }

  @Override
  public LoginResponse refreshToken(String token) {
    RefreshToken refreshToken = refreshTokenService.findByToken(token)
        .orElseThrow(
            () -> new ResourceNotFoundException(RefreshToken.class.getName(), "token", token));

    refreshToken = refreshTokenService.verifyExpiration(refreshToken);

    String username = refreshToken.getUser().getUsername();

    // Load user details and create authentication object
    UserDetails userDetails = userDetailsService.findByUsername(username).block();
    Objects.requireNonNull(userDetails);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    // Generate new tokens
    String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(username);

    UserDTO userDTO = userService.getUserDetails(username);

    return LoginResponse.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken.getToken())
        .tokenType("Bearer")
        .expiresIn(jwtTokenProvider.getExpirationTime())
        .user(userDTO)
        .build();
  }
} 