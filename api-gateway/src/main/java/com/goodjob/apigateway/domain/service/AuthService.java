package com.goodjob.apigateway.domain.service;

import com.goodjob.apigateway.domain.dto.response.LoginResponse;
import com.goodjob.apigateway.domain.dto.request.RegisterRequest;
import com.goodjob.apigateway.domain.dto.response.UserDTO;
import com.goodjob.apigateway.domain.dto.request.LoginRequest;
import com.goodjob.common.application.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest loginRequest);
    UserDTO register(RegisterRequest registerRequest);
    LoginResponse refreshToken(String refreshToken);
} 