package com.om.collaboration.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor
        implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(
            @NonNull Message<?> message,
            @NonNull MessageChannel channel) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(
                accessor.getCommand())) {

            String authHeader =
                    accessor.getFirstNativeHeader(
                            "Authorization");

            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {

                String token =
                        authHeader.substring(7);

                if (jwtService.isValid(token)) {

                    String email =
                            jwtService.extractEmail(token);

                    UsernamePasswordAuthenticationToken
                            authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    null);

                    accessor.setUser(authentication);

                    Objects.requireNonNull(accessor.getSessionAttributes())
                            .put("user", authentication);

                    System.out.println(
                            "USER ATTACHED");
                }
            }
        }

        else {

            Object user =
                    Objects.requireNonNull(accessor.getSessionAttributes())
                            .get("user");

            if (user instanceof UsernamePasswordAuthenticationToken auth) {

                accessor.setUser(auth);
            }
        }

        return message;
    }
}