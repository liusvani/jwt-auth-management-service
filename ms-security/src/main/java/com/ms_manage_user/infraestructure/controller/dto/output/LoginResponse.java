package com.ms_manage_user.infraestructure.controller.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;

 public class LoginResponse {
    private String token;
    public LoginResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
