package com.goodjob.notification.api.rest;

import com.goodjob.notification.domain.notification.dto.NotificationPreferenceDTO;
import com.goodjob.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing notification preferences.
 */
@RestController
@RequestMapping("/api/v1/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<NotificationPreferenceDTO> getUserPreferences(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        NotificationPreferenceDTO preferences = notificationService.getUserPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<NotificationPreferenceDTO> updateUserPreferences(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody NotificationPreferenceDTO preferencesDTO
    ) {
        String userId = jwt.getSubject();
        NotificationPreferenceDTO updatedPreferences = notificationService.updateUserPreferences(userId, preferencesDTO);
        return ResponseEntity.ok(updatedPreferences);
    }
} 