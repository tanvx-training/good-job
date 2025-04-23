package com.goodjob.apigateway.application.service;

import com.goodjob.apigateway.application.dto.response.UserDTO;

public interface UserService {
    UserDTO createUser(String username, String password);
    UserDTO getUserDetails(String username);
    boolean existsByUsername(String username);
} 