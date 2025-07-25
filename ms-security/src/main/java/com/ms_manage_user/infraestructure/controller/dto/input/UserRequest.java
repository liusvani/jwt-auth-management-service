package com.ms_manage_user.infraestructure.controller.dto.input;

import com.ms_manage_user.domain.model.UserRol;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UserRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRol role;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
