package com.om.collaboration.controller;

import com.om.collaboration.entity.Notification;
import com.om.collaboration.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getMyNotifications(
            Authentication authentication) {

        return notificationService
                .getMyNotifications(
                        authentication.getName()
                );
    }
}