package com.om.collaboration.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPresence {

    private String email;

    private String status;
}