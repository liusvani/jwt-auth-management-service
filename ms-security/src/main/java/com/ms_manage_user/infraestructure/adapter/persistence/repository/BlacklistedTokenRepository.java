package com.ms_manage_user.infraestructure.adapter.persistence.repository;

import com.ms_manage_user.domain.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, UUID> {
    boolean existsByToken(String token);

    void deleteByExpiryDateBefore(Instant instant);
    List<BlacklistedToken> findByExpiryDateBefore(Instant instant);
}

