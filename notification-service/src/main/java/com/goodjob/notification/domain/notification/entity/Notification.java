package com.goodjob.notification.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entity representing a notification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
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