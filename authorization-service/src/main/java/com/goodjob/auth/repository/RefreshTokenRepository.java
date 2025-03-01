package com.goodjob.auth.repository;

import com.goodjob.auth.entity.RefreshToken;
import com.goodjob.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for RefreshToken entity operations.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find a refresh token by token value.
     *
     * @param token the token value to search for
     * @return an Optional containing the refresh token if found, or empty if not found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Find a refresh token by user.
     *
     * @param user the user to search for
     * @return an Optional containing the refresh token if found, or empty if not found
     */
    Optional<RefreshToken> findByUser(User user);

    /**
     * Delete refresh tokens for a user.
     *
     * @param user the user whose refresh tokens should be deleted
     * @return the number of refresh tokens deleted
     */
    @Modifying
    int deleteByUser(User user);
} 