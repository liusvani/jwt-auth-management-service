package com.ms_manage_user.infraestructure.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Credenciales para iniciar sesión")
public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Schema(example = "usuario123")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(example = "secreto123")
    private String password;

    // Getters y setters
}
