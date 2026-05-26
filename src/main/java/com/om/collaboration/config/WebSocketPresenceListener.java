package com.om.collaboration.config;

import com.om.collaboration.dto.UserPresence;
import com.om.collaboration.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketPresenceListener {

    private final PresenceService presenceService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleConnect(
            SessionConnectEvent event) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(
                        event.getMessage());

        Object user =
                accessor.getSessionAttributes() != null
                        ? accessor.getSessionAttributes()
                          .get("user")
                        : null;

        if (user instanceof
                UsernamePasswordAuthenticationToken auth) {

            String email = auth.getName();

            presenceService.userOnline(email);

            System.out.println(
                    email + " is ONLINE");

            messagingTemplate.convertAndSend(
                    "/topic/presence",
                    UserPresence.builder()
                            .email(email)
                            .status("ONLINE")
                            .build()
            );
        }
    }

    @EventListener
    public void handleDisconnect(
            SessionDisconnectEvent event) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(
                        event.getMessage());

        Object user =
                accessor.getSessionAttributes() != null
                        ? accessor.getSessionAttributes()
                          .get("user")
                        : null;

        if (user instanceof
                UsernamePasswordAuthenticationToken auth) {

            String email = auth.getName();

            presenceService.userOffline(email);

            System.out.println(
                    email + " is OFFLINE");

            messagingTemplate.convertAndSend(
                    "/topic/presence",
                    UserPresence.builder()
                            .email(email)
                            .status("OFFLINE")
                            .build()
            );
        }
    }
}