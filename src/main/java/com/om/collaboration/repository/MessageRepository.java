package com.om.collaboration.repository;

import com.om.collaboration.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findByTeamIdOrderBySentAtAsc(
            Long teamId
    );
}