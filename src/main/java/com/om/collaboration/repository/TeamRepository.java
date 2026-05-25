package com.om.collaboration.repository;

import com.om.collaboration.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository
        extends JpaRepository<Team, Long> {
}