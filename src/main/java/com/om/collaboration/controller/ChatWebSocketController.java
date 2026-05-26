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

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(
            ChatMessage chatMessage,
            Message<?> message) {

        Map<String, Object> sessionAttributes =
                (Map<String, Object>)
                        message.getHeaders()
                                .get("simpSessionAttributes");

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken)
                        sessionAttributes.get("user");

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