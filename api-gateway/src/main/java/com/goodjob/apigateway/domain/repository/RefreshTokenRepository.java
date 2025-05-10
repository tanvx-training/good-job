package com.goodjob.apigateway.domain.repository;

import com.goodjob.apigateway.domain.entity.RefreshToken;
import com.goodjob.apigateway.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndDeleteFlg(String token, boolean deleteFlg);

    List<RefreshToken> findByUser(User user);
    
    @Modifying
    void deleteByUser(User user);
} 