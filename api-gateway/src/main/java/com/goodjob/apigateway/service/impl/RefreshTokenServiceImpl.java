package com.goodjob.apigateway.service.impl;

import com.goodjob.apigateway.entity.RefreshToken;
import com.goodjob.apigateway.entity.User;
import com.goodjob.apigateway.repository.RefreshTokenRepository;
import com.goodjob.apigateway.repository.UserRepository;
import com.goodjob.apigateway.service.RefreshTokenService;
import com.goodjob.common.exception.BadRequestException;
import com.goodjob.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${app.jwt.expiration.refresh}")
  private Long refreshTokenDurationMs;

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public RefreshToken createRefreshToken(String username) {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() ->
            new UsernameNotFoundException("User not found with username: " + username));

    // Remove existing refresh token if exists
    List<RefreshToken> existingTokens = refreshTokenRepository.findByUser(user);
    if (!existingTokens.isEmpty()) {
      refreshTokenRepository.deleteAll(existingTokens);
      refreshTokenRepository.flush();
    }

    RefreshToken refreshToken = RefreshToken.builder()
        .user(user)
        .token(UUID.randomUUID().toString())
        .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
        .createdOn(LocalDateTime.now())
        .deleteFlg(false)
        .build();

    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new BadRequestException("Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByTokenAndDeleteFlg(token, false);
  }

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException(User.class.getName(), "id", userId));
    refreshTokenRepository.deleteByUser(user);
  }
} 