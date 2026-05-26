package com.om.collaboration.controller;

import com.om.collaboration.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;

    @GetMapping("/online")
    public Set<String> getOnlineUsers() {

        return presenceService.getOnlineUsers();
    }
}