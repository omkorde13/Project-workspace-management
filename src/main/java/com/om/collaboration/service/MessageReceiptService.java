package com.om.collaboration.service;

import com.om.collaboration.entity.MessageReceipt;
import com.om.collaboration.repository.MessageReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageReceiptService {

    private final MessageReceiptRepository
            messageReceiptRepository;

    public void createReceipt(
            Long messageId,
            String userEmail) {

        MessageReceipt receipt =
                MessageReceipt.builder()
                        .messageId(messageId)
                        .userEmail(userEmail)
                        .status("DELIVERED")
                        .updatedAt(
                                LocalDateTime.now())
                        .build();

        messageReceiptRepository.save(
                receipt);
    }

    public void markAsRead(
            Long messageId,
            String userEmail) {

        MessageReceipt receipt =
                messageReceiptRepository
                        .findByMessageIdAndUserEmail(
                                messageId,
                                userEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Receipt not found"));

        receipt.setStatus("READ");

        receipt.setUpdatedAt(
                LocalDateTime.now());

        messageReceiptRepository.save(
                receipt);
    }
}