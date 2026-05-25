package com.om.collaboration.controller;

import com.om.collaboration.dto.ChatMessage;
import com.om.collaboration.dto.MessageResponse;
import com.om.collaboration.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(
            ChatMessage chatMessage,
            Message<?> message) {

        Object attributesObj =
                message.getHeaders()
                        .get("simpSessionAttributes");

        if (!(attributesObj instanceof java.util.Map<?, ?> attributes)) {
            throw new RuntimeException(
                    "Session attributes not found");
        }

        Object authObj = attributes.get("user");

        if (!(authObj instanceof
                UsernamePasswordAuthenticationToken auth)) {

            throw new RuntimeException(
                    "User not authenticated");
        }

        String email = auth.getName();

        System.out.println("USER = " + email);

        MessageResponse saved =
                messageService.saveMessage(
                        chatMessage.getTeamId(),
                        email,
                        chatMessage.getContent()
                );

        messagingTemplate.convertAndSend(
                "/topic/team/" +
                        chatMessage.getTeamId(),
                saved
        );
    }
}