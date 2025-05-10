package com.goodjob.notification.api.websocket;

import com.goodjob.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handler for WebSocket events.
 */
@Component
@RequiredArgsConstructor
public class WebSocketHandler {

    private final NotificationService notificationService;
    private final Map<String, String> sessionIdToUserIdMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        
        // Extract user ID from headers or authentication
        String userId = extractUserId(headers);
        
        if (userId != null) {
            sessionIdToUserIdMap.put(sessionId, userId);
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        
        sessionIdToUserIdMap.remove(sessionId);
    }

    private String extractUserId(SimpMessageHeaderAccessor headers) {
        // Extract user ID from authentication or custom headers
        // This is a simplified example, in a real application you would extract from JWT or session
        return headers.getUser() != null ? headers.getUser().getName() : null;
    }
} 