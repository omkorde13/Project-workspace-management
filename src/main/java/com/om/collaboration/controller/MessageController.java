package com.om.collaboration.controller;

import com.om.collaboration.dto.MessageResponse;
import com.om.collaboration.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{teamId}")
    public List<MessageResponse> getMessages(
            @PathVariable Long teamId,
            Authentication authentication) {

        return messageService.getMessages(
                teamId,
                authentication.getName()
        );
    }
}