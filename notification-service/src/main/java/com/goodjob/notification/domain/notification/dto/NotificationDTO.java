package com.goodjob.notification.domain.notification.dto;

import com.goodjob.notification.domain.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for notification data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    
    private String id;
    private String userId;
    private String title;
    private String content;
    private NotificationType type;
    private String sourceId;
    private String sourceType;
    private Map<String, Object> data;
    private boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
} 