package com.ms_manage_user.infraestructure.controller.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
