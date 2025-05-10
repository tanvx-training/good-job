package com.goodjob.apigateway.domain.service;

import com.goodjob.apigateway.domain.dto.response.UserDTO;

public interface UserService {
    UserDTO createUser(String username, String password);
    UserDTO getUserDetails(String username);
    boolean existsByUsername(String username);
} 