package com.goodjob.apigateway.service;

import com.goodjob.apigateway.dto.response.LoginResponse;
import com.goodjob.apigateway.dto.request.RegisterRequest;
import com.goodjob.apigateway.dto.response.UserDTO;
import com.goodjob.apigateway.dto.request.LoginRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    UserDTO register(RegisterRequest registerRequest);
    LoginResponse refreshToken(String refreshToken);
} 