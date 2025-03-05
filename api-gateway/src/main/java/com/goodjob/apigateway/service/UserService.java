package com.goodjob.apigateway.service;

import com.goodjob.apigateway.dto.response.UserDTO;

public interface UserService {
    UserDTO createUser(String username, String password);
    UserDTO getUserDetails(String username);
    boolean existsByUsername(String username);
} 