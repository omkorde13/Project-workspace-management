package com.om.collaboration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamMemberResponse {

    private Long id;

    private String name;

    private String email;

    private String role;
}