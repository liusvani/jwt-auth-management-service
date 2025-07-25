package com.ms_manage_user.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refrestoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {
    @Id
    private String token;

    private String username;

    private Instant expiryDate;


}

