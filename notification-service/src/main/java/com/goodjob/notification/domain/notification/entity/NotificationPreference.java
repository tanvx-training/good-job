package com.goodjob.notification.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing user notification preferences.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    
    @Column(name = "email_enabled")
    private boolean emailEnabled;
    
    @Column(name = "push_enabled")
    private boolean pushEnabled;
    
    @Column(name = "in_app_enabled")
    private boolean inAppEnabled;
    
    @Column(name = "profile_view_notifications")
    private boolean profileViewNotifications;
    
    @Column(name = "connection_notifications")
    private boolean connectionNotifications;
    
    @Column(name = "message_notifications")
    private boolean messageNotifications;
    
    @Column(name = "job_notifications")
    private boolean jobNotifications;
    
    @Column(name = "application_notifications")
    private boolean applicationNotifications;
    
    @Column(name = "event_notifications")
    private boolean eventNotifications;
} 