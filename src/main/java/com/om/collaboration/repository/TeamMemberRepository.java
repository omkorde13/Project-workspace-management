package com.om.collaboration.repository;

import com.om.collaboration.entity.Team;
import com.om.collaboration.entity.TeamMember;
import com.om.collaboration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository
        extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByTeamAndUser(
            Team team,
            User user
    );

    List<TeamMember> findByUser(User user);

    List<TeamMember> findByTeam(Team team);

    boolean existsByTeamAndUser(
            Team team,
            User user
    );
}