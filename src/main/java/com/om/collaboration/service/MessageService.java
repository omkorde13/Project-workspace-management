package com.om.collaboration.service;

import com.om.collaboration.dto.MessageResponse;
import com.om.collaboration.entity.Message;
import com.om.collaboration.entity.Team;
import com.om.collaboration.repository.MessageRepository;
import com.om.collaboration.repository.TeamMemberRepository;
import com.om.collaboration.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final TeamMemberRepository teamMemberRepository;
    private final MessageRepository messageRepository;
    private final TeamRepository teamRepository;
    public MessageResponse saveMessage(
            Long teamId,
            String senderEmail,
            String content) {

        Message message = Message.builder()
                .teamId(teamId)
                .senderEmail(senderEmail)
                .content(content)
                .sentAt(LocalDateTime.now())
                .build();

        Message saved =
                messageRepository.save(message);

        return MessageResponse.builder()
                .id(saved.getId())
                .teamId(saved.getTeamId())
                .senderEmail(saved.getSenderEmail())
                .content(saved.getContent())
                .sentAt(saved.getSentAt())
                .build();
    }
    public List<MessageResponse> getMessages(
            Long teamId,
            String email) {

        boolean isMember =
                teamMemberRepository
                        .existsByTeamIdAndUserEmail(
                                teamId,
                                email);

        if (!isMember) {
            throw new RuntimeException(
                    "Access denied");
        }

        return messageRepository
                .findByTeamIdOrderBySentAtAsc(teamId)
                .stream()
                .map(message ->
                        MessageResponse.builder()
                                .id(message.getId())
                                .teamId(message.getTeamId())
                                .senderEmail(
                                        message.getSenderEmail())
                                .content(
                                        message.getContent())
                                .sentAt(
                                        message.getSentAt())
                                .build())
                .toList();
    }

}