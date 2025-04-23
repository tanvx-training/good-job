package com.goodjob.apigateway.application.service;

import com.goodjob.apigateway.application.dto.response.LoginResponse;
import com.goodjob.apigateway.application.dto.request.RegisterRequest;
import com.goodjob.apigateway.application.dto.response.UserDTO;
import com.goodjob.apigateway.application.dto.request.LoginRequest;
import com.goodjob.common.application.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest loginRequest);
    UserDTO register(RegisterRequest registerRequest);
    LoginResponse refreshToken(String refreshToken);
} 