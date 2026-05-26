package com.om.collaboration.repository;

import com.om.collaboration.entity.MessageReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageReceiptRepository
        extends JpaRepository<MessageReceipt, Long> {

    List<MessageReceipt>
    findByMessageId(Long messageId);

    List<MessageReceipt>
    findByUserEmail(String userEmail);

    Optional<MessageReceipt>
    findByMessageIdAndUserEmail(
            Long messageId,
            String userEmail
    );
}