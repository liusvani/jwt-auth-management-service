package com.ms_manage_user.application.service;

import com.ms_manage_user.domain.model.RefreshTokenEntity;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public void store(String token, String username, Instant expiryDate) {
        repository.save(new RefreshTokenEntity(token, username, expiryDate));
    }

    public boolean isValid(String token) {
        return repository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }

    public String getUsername(String token) {
        return repository.findByToken(token)
                .map(RefreshTokenEntity::getUsername)
                .orElseThrow(() -> new RuntimeException("Refresh token invalid"));
    }

    @Transactional // Esto asegura que haya una transacción activa
    public void invalidateAll(String username) {
        repository.deleteByUsername(username);
    }
}

