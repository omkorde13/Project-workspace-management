package com.om.collaboration.controller;

import com.om.collaboration.dto.AuditLogResponse;
import com.om.collaboration.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/team/{teamId}")
    public List<AuditLogResponse> getTeamLogs(
            @PathVariable Long teamId) {

        return auditLogService.getTeamLogs(teamId);
    }
}