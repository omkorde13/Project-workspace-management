package com.om.collaboration.service;

import com.om.collaboration.entity.Notification;
import com.om.collaboration.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository
            notificationRepository;

    public void createNotification(
            String recipientEmail,
            String message) {

        Notification notification =
                Notification.builder()
                        .recipientEmail(
                                recipientEmail)
                        .message(message)
                        .read(false)
                        .createdAt(
                                LocalDateTime.now())
                        .build();

        notificationRepository.save(
                notification);
    }

    public List<Notification>
    getMyNotifications(
            String email) {

        return notificationRepository
                .findByRecipientEmailOrderByCreatedAtDesc(
                        email);
    }


    }
