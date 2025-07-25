package com.ms_manage_user.infraestructure.adapter.persistence.scheduler;

import com.ms_manage_user.application.service.TokenBlacklistService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {
    private final TokenBlacklistService service;

    public TokenCleanupScheduler(TokenBlacklistService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 * * * * *") // cada un minuto se ejecuta la tarea programada
    public void scheduleCleanup() {
        service.removeExpiredTokens();
    }
}

