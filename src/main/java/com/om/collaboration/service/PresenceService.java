package com.om.collaboration.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class PresenceService {

    private final Set<String> onlineUsers =
            ConcurrentHashMap.newKeySet();

    public void userOnline(String email) {
        onlineUsers.add(email);
    }

    public void userOffline(String email) {
        onlineUsers.remove(email);
    }

    public boolean isOnline(String email) {
        return onlineUsers.contains(email);
    }

}