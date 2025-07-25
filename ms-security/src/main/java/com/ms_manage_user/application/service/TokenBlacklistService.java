package com.ms_manage_user.application.service;

import com.ms_manage_user.domain.model.BlacklistedToken;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.BlacklistedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TokenBlacklistService {

    @Autowired
    private BlacklistedTokenRepository repository;
    public void blacklistToken(String token, Instant expiryDate) {
        BlacklistedToken blacklisted = new BlacklistedToken();
        blacklisted.setToken(token);
        blacklisted.setExpiryDate(expiryDate);
        repository.save(blacklisted);
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }

    public void removeExpiredTokens() {
        Instant now = Instant.now();
        List<BlacklistedToken> expiredTokens = repository.findByExpiryDateBefore(now);
        if (!expiredTokens.isEmpty()) {
            repository.deleteAll(expiredTokens);
        }
    }
}

