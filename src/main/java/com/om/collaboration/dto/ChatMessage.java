package com.om.collaboration.dto;

import lombok.Data;

@Data
public class ChatMessage {

    private Long teamId;

    private String content;
}