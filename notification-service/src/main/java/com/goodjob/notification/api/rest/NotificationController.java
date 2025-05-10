package com.goodjob.notification.api.rest;

import com.goodjob.notification.domain.notification.dto.NotificationDTO;
import com.goodjob.notification.domain.notification.dto.NotificationEvent;
import com.goodjob.notification.domain.notification.entity.NotificationType;
import com.goodjob.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing notifications.
 */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<NotificationDTO>> getUserNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        String userId = jwt.getSubject();
        PageRequest pageRequest = PageRequest.of(
                page, 
                size, 
                Sort.Direction.fromString(direction), 
                sort
        );
        
        Page<NotificationDTO> notifications = notificationService.getUserNotifications(userId, pageRequest);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<NotificationDTO>> getUnreadNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        String userId = jwt.getSubject();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<NotificationDTO> notifications = notificationService.getUserUnreadNotifications(userId, pageRequest);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/count/unread")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<NotificationDTO> getNotificationById(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        
        // Verify ownership
        if (!notification.getUserId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<NotificationDTO> markAsRead(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        
        // Verify ownership
        if (!notification.getUserId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        NotificationDTO updatedNotification = notificationService.markAsRead(id);
        return ResponseEntity.ok(updatedNotification);
    }

    @PatchMapping("/read-all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        
        // Verify ownership
        if (!notification.getUserId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recent")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<NotificationDTO>> getRecentNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) NotificationType type,
            @RequestParam(defaultValue = "5") int limit
    ) {
        String userId = jwt.getSubject();
        List<NotificationDTO> notifications = notificationService.getRecentNotifications(userId, type, limit);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NotificationDTO> createTestNotification(@RequestBody NotificationEvent event) {
        NotificationDTO notification = notificationService.createNotification(event);
        notificationService.processNotificationEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
} 