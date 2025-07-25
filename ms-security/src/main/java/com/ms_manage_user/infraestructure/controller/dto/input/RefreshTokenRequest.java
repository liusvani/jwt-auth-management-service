package com.ms_manage_user.infraestructure.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {

    private String refreshToken;

    // Getter y setter generados
}

