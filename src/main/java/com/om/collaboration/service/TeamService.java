package com.om.collaboration.service;

import com.om.collaboration.dto.CreateTeamRequest;
import com.om.collaboration.dto.TeamMemberResponse;
import com.om.collaboration.entity.Team;
import com.om.collaboration.entity.TeamMember;
import com.om.collaboration.entity.User;
import com.om.collaboration.repository.TeamMemberRepository;
import com.om.collaboration.repository.TeamRepository;
import com.om.collaboration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public Team createTeam(
            CreateTeamRequest request,
            String creatorEmail) {

        User creator = userRepository.findByEmail(
                creatorEmail
        ).orElseThrow(() ->
                new RuntimeException("User not found"));

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(creatorEmail)
                .createdAt(LocalDateTime.now())
                .build();

        Team savedTeam = teamRepository.save(team);

        TeamMember adminMember =
                TeamMember.builder()
                        .team(savedTeam)
                        .user(creator)
                        .role("ADMIN")
                        .joinedAt(LocalDateTime.now())
                        .build();

        teamMemberRepository.save(adminMember);

        return savedTeam;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void joinTeam(
            Long teamId,
            String email) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (teamMemberRepository
                .findByTeamAndUser(team, user)
                .isPresent()) {

            throw new RuntimeException(
                    "Already joined");
        }

        TeamMember member =
                TeamMember.builder()
                        .team(team)
                        .user(user)
                        .role("MEMBER")
                        .joinedAt(LocalDateTime.now())
                        .build();

        teamMemberRepository.save(member);
    }

    public List<Team> getMyTeams(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return teamMemberRepository
                .findByUser(user)
                .stream()
                .map(TeamMember::getTeam)
                .toList();
    }

    public Team getTeam(Long teamId) {

        return teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));
    }

    public List<TeamMemberResponse> getTeamMembers(
            Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        return teamMemberRepository
                .findByTeam(team)
                .stream()
                .map(member ->
                        TeamMemberResponse.builder()
                                .id(member.getUser().getId())
                                .name(member.getUser().getName())
                                .email(member.getUser().getEmail())
                                .role(member.getRole())
                                .build())
                .toList();
    }

    public void leaveTeam(
            Long teamId,
            String email) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        TeamMember member =
                teamMemberRepository
                        .findByTeamAndUser(team, user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Not a team member"));

        if ("ADMIN".equals(member.getRole())) {

            long adminCount =
                    teamMemberRepository
                            .findByTeam(team)
                            .stream()
                            .filter(m ->
                                    "ADMIN".equals(
                                            m.getRole()))
                            .count();

            if (adminCount == 1) {

                throw new RuntimeException(
                        "Cannot leave. Transfer admin role first.");
            }
        }

        teamMemberRepository.delete(member);
    }

    private TeamMember getAdminMember(
            Long teamId,
            String email) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        TeamMember member =
                teamMemberRepository
                        .findByTeamAndUser(team, user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Not a member"));

        if (!"ADMIN".equals(member.getRole())) {

            throw new RuntimeException(
                    "Only admins can perform this action");
        }

        return member;
    }

    public void promoteMember(
            Long teamId,
            Long userId,
            String adminEmail) {

        getAdminMember(teamId, adminEmail);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        User targetUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        TeamMember member =
                teamMemberRepository
                        .findByTeamAndUser(
                                team,
                                targetUser)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Member not found"));

        member.setRole("ADMIN");

        teamMemberRepository.save(member);
    }

    public void removeMember(
            Long teamId,
            Long userId,
            String adminEmail) {

        getAdminMember(teamId, adminEmail);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        User targetUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        TeamMember member =
                teamMemberRepository
                        .findByTeamAndUser(
                                team,
                                targetUser)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Member not found"));

        teamMemberRepository.delete(member);
    }
}