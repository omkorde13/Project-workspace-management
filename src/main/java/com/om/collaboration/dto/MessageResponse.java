package com.om.collaboration.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {

    private Long id;

    private Long teamId;

    private String senderEmail;

    private String content;

    private LocalDateTime sentAt;
}