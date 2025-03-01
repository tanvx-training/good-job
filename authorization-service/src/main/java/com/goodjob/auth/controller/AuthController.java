package com.goodjob.auth.controller;

import com.goodjob.auth.dto.JwtResponse;
import com.goodjob.auth.dto.LoginRequest;
import com.goodjob.auth.dto.RefreshTokenRequest;
import com.goodjob.auth.dto.RegisterRequest;
import com.goodjob.auth.entity.User;
import com.goodjob.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user.
     *
     * @param registerRequest the registration request
     * @return the created user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.registerUser(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Authenticate a user and generate JWT token.
     *
     * @param loginRequest the login request
     * @return the JWT response
     */
    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT tokens")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Refresh an access token using a refresh token.
     *
     * @param request the refresh token request
     * @return the new JWT response
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refreshes an access token using a refresh token")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        JwtResponse jwtResponse = authService.refreshToken(request);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Log out a user by deleting their refresh token.
     *
     * @param userId the user ID
     * @return a success message
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Logs out a user by invalidating their refresh token")
    public ResponseEntity<String> logout(@RequestParam Long userId) {
        int result = authService.logoutUser(userId);
        if (result > 0) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.ok("No active session found");
        }
    }
} 