package com.om.collaboration.controller;

import com.om.collaboration.dto.CreateTeamRequest;
import com.om.collaboration.dto.TeamMemberResponse;
import com.om.collaboration.entity.Team;
import com.om.collaboration.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public Team createTeam(
            @Valid @RequestBody CreateTeamRequest request,
            Authentication authentication) {

        return teamService.createTeam(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<Team> getTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/{teamId}/join")
    public String joinTeam(
            @PathVariable Long teamId,
            Authentication authentication) {

        teamService.joinTeam(
                teamId,
                authentication.getName()
        );

        return "Joined successfully";
    }

    @GetMapping("/my")
    public List<Team> myTeams(
            Authentication authentication) {

        return teamService.getMyTeams(
                authentication.getName()
        );
    }

    @GetMapping("/{teamId}")
    public Team getTeam(
            @PathVariable Long teamId) {

        return teamService.getTeam(teamId);
    }

    @GetMapping("/{teamId}/members")
    public List<TeamMemberResponse> getMembers(
            @PathVariable Long teamId) {

        return teamService.getTeamMembers(teamId);
    }

    @DeleteMapping("/{teamId}/leave")
    public String leaveTeam(
            @PathVariable Long teamId,
            Authentication authentication) {

        teamService.leaveTeam(
                teamId,
                authentication.getName());

        return "Left team successfully";
    }

    @PostMapping("/{teamId}/promote/{userId}")
    public String promoteMember(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            Authentication authentication) {

        teamService.promoteMember(
                teamId,
                userId,
                authentication.getName());

        return "Member promoted to ADMIN";
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public String removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            Authentication authentication) {

        teamService.removeMember(
                teamId,
                userId,
                authentication.getName());

        return "Member removed successfully";
    }
}