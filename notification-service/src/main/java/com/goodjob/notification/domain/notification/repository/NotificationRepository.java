package com.goodjob.notification.domain.notification.repository;

import com.goodjob.notification.domain.notification.entity.Notification;
import com.goodjob.notification.domain.notification.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Notification entity.
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    
    Page<Notification> findByUserId(String userId, Pageable pageable);
    
    Page<Notification> findByUserIdAndRead(String userId, boolean read, Pageable pageable);
    
    List<Notification> findByUserIdAndTypeAndCreatedAtAfter(String userId, NotificationType type, LocalDateTime after);
    
    long countByUserIdAndReadFalse(String userId);
} 