package com.ms_manage_user.infraestructure.controller.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Boolean enabled;
    private String createdAt;
    private String updatedAt;
}
