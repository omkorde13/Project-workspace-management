package com.om.collaboration.repository;

import com.om.collaboration.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByTeamIdOrderByCreatedAtDesc(
            Long teamId);
}