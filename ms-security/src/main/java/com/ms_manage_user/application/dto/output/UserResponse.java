package com.ms_manage_user.application.dto.output;

import com.ms_manage_user.domain.model.UserRol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRol role;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
