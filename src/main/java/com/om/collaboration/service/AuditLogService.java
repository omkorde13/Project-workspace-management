package com.om.collaboration.service;

import com.om.collaboration.dto.AuditLogResponse;
import com.om.collaboration.entity.AuditLog;
import com.om.collaboration.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void saveLog(
            Long teamId,
            String userEmail,
            String action) {

        AuditLog log = AuditLog.builder()
                .teamId(teamId)
                .userEmail(userEmail)
                .action(action)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLogResponse> getTeamLogs(
            Long teamId) {

        return auditLogRepository
                .findByTeamIdOrderByCreatedAtDesc(teamId)
                .stream()
                .map(log ->
                        AuditLogResponse.builder()
                                .id(log.getId())
                                .teamId(log.getTeamId())
                                .userEmail(log.getUserEmail())
                                .action(log.getAction())
                                .createdAt(log.getCreatedAt())
                                .build())
                .toList();
    }

}