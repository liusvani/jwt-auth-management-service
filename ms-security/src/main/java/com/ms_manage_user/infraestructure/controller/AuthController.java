package com.ms_manage_user.infraestructure.controller;

import com.ms_manage_user.application.service.RefreshTokenService;
import com.ms_manage_user.application.service.TokenBlacklistService;
import com.ms_manage_user.application.service.UserService;
import com.ms_manage_user.domain.model.User;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.UserRepository;
import com.ms_manage_user.infraestructure.adapter.security.CustomUserDetailsService;
import com.ms_manage_user.infraestructure.adapter.security.JwtUtil;
import com.ms_manage_user.infraestructure.controller.dto.input.LoginRequest;
import com.ms_manage_user.infraestructure.controller.dto.output.LoginResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Login y generación de token JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    private final UserRepository userRepository;

    private final TokenBlacklistService blacklistService;

    private final RefreshTokenService refreshTokenService;

    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService, UserRepository userRepository, TokenBlacklistService blacklistService, RefreshTokenService refreshTokenService, CustomUserDetailsService customUserDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.blacklistService = blacklistService;
        this.refreshTokenService = refreshTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }


    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login del usuario solo si esta registrado y su estado es enabled.")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled");
            }

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Guardar el refresh token en la base de datos
            Instant expiryDate = Instant.now().plusMillis(jwtUtil.getRefreshTokenExpiration());
            refreshTokenService.store(refreshToken, userDetails.getUsername(), expiryDate);

            System.out.println("Access token generado: " + accessToken);
            System.out.println("Refresh token generado: " + refreshToken);

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout del usuario, revoca access y refresh tokens.")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> payload) {
        try {
            String accessRaw = payload.get("accessToken");
            String refreshToken = payload.get("refreshToken");

            if (accessRaw == null || refreshToken == null) {
                return ResponseEntity.badRequest().body("The accessToken or refreshToken is missing in the request body.");
            }

            String accessToken = accessRaw.replace("Bearer ", "");
            try {
                Instant accessExpiry = jwtUtil.extractExpiration(accessToken).toInstant();
                blacklistService.blacklistToken(accessToken, accessExpiry);
            } catch (ExpiredJwtException e) {
                System.out.println("Access token expirado, se revoca igualmente.");
                blacklistService.blacklistToken(accessToken, Instant.now());
            }

            String username = jwtUtil.extractUsername(refreshToken);
            refreshTokenService.invalidateAll(username);
            System.out.println("Refresh tokens revoked for the user: " + username);

            return ResponseEntity.ok("Logout successful: access and refresh tokens revoked.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout error: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar access token", description = "Usa refresh token para obtener uno nuevo.")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Validar refresh token
        if (!refreshTokenService.isValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refres token invalid or expired.");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }



}
