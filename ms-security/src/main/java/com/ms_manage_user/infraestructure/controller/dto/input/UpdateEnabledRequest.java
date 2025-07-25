package com.ms_manage_user.infraestructure.controller.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateEnabledRequest {
    @NotNull(message = "Field enabled cannot be empty.")
    private Boolean enabled;
}
