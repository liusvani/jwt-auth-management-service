package com.ms_manage_user.infraestructure.adapter.persistence.repository;

import com.ms_manage_user.domain.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUsername(String username);
}

