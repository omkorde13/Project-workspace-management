package com.om.collaboration.controller;

import com.om.collaboration.dto.AuthResponse;
import com.om.collaboration.dto.LoginRequest;
import com.om.collaboration.dto.LoginResponse;
import com.om.collaboration.dto.RegisterRequest;
import com.om.collaboration.entity.User;
import com.om.collaboration.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new LoginResponse(token);
    }
}