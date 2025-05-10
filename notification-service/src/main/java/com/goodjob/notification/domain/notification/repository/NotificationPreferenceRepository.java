package com.goodjob.notification.domain.notification.repository;

import com.goodjob.notification.domain.notification.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for NotificationPreference entity.
 */
@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    
    Optional<NotificationPreference> findByUserId(String userId);
    
    boolean existsByUserId(String userId);
} 