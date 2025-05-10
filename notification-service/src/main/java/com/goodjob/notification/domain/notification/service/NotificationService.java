package com.goodjob.notification.domain.notification.service;

import com.goodjob.notification.domain.notification.dto.NotificationDTO;
import com.goodjob.notification.domain.notification.dto.NotificationEvent;
import com.goodjob.notification.domain.notification.dto.NotificationPreferenceDTO;
import com.goodjob.notification.domain.notification.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service for handling notifications.
 */
public interface NotificationService {

    NotificationDTO createNotification(NotificationEvent event);
    
    Page<NotificationDTO> getUserNotifications(String userId, Pageable pageable);
    
    Page<NotificationDTO> getUserUnreadNotifications(String userId, Pageable pageable);
    
    NotificationDTO getNotificationById(String id);
    
    NotificationDTO markAsRead(String id);
    
    void markAllAsRead(String userId);
    
    void deleteNotification(String id);
    
    long getUnreadCount(String userId);
    
    List<NotificationDTO> getRecentNotifications(String userId, NotificationType type, int limit);
    
    NotificationPreferenceDTO getUserPreferences(String userId);
    
    NotificationPreferenceDTO updateUserPreferences(String userId, NotificationPreferenceDTO preferences);
    
    void processNotificationEvent(NotificationEvent event);
} 