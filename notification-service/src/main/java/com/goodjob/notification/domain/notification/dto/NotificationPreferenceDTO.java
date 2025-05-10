package com.goodjob.notification.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for notification preference data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceDTO {
    
    private Long id;
    private String userId;
    private boolean emailEnabled;
    private boolean pushEnabled;
    private boolean inAppEnabled;
    private boolean profileViewNotifications;
    private boolean connectionNotifications;
    private boolean messageNotifications;
    private boolean jobNotifications;
    private boolean applicationNotifications;
    private boolean eventNotifications;
} 