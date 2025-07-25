package com.ms_manage_user.infraestructure.adapter.persistence.repository;

import com.ms_manage_user.domain.model.User;
import com.ms_manage_user.domain.model.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    long countByRole(UserRol role);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> findUserById(UUID uuid);
}
