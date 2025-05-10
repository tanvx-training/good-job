package com.goodjob.notification.domain.notification.dto;

import com.goodjob.notification.domain.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for notification events.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    
    private String userId;
    private String title;
    private String content;
    private NotificationType type;
    private String sourceId;
    private String sourceType;
    private Map<String, Object> data;
    private boolean sendEmail;
    private boolean sendPush;
    private boolean sendInApp;
} 