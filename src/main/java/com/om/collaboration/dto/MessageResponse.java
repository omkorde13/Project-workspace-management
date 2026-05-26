package com.om.collaboration.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private Long id;

    private Long teamId;

    private String senderEmail;

    private String content;

    private LocalDateTime sentAt;
}