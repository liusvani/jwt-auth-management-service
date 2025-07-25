package com.ms_manage_user.infraestructure.controller.dto.input;

import com.ms_manage_user.domain.model.UserRol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

//RegisterRequest para /auth/register
@Getter
@Setter
@Schema(description = "Datos para registrar un nuevo usuario")
public class RegisterRequest {

    @jakarta.validation.constraints.NotBlank(message = "User name cannot be empty.")
    @jakarta.validation.constraints.Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    @jakarta.validation.constraints.Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "Only letters, numbers, periods, underscores, and hyphens are allowed."
    )
    private String username;

    @Email(message = "Must be a valid email address.")
    @NotBlank(message = "The email cannot be empty.")
    private String email;


    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 64, message = "The password must be between 8 and 64 characters.")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 64, message = "The password must be between 8 and 64 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must have uppercase, lowercase, number and symbol.")
    private String password;


    @Pattern(regexp = "^(?! )[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?: [A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "First name must contain only letters and spaces.")
    private String firstName;

    @Pattern(regexp = "^(?! )[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?: [A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Last name must contain only letters and spaces.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "You must select a valid role: USER or ADMIN.")
    private UserRol role;

    private boolean enabled;
}

