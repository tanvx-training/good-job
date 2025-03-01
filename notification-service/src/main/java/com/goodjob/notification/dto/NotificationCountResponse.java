package com.goodjob.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for returning the count of unread notifications.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCountResponse {
    
    /**
     * The user ID.
     */
    private String userId;
    
    /**
     * The count of unread notifications.
     */
    private long unreadCount;
} 