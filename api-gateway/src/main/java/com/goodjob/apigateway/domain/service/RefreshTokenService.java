package com.goodjob.apigateway.domain.service;

import com.goodjob.apigateway.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
} 