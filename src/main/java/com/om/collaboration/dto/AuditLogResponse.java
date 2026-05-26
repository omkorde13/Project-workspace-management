package com.om.collaboration.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;

    private Long teamId;

    private String userEmail;

    private String action;

    private LocalDateTime createdAt;
}