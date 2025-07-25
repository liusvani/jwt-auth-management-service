package com.ms_manage_user.application.service;

import com.ms_manage_user.application.dto.input.UserRequest;
import com.ms_manage_user.application.dto.output.UserResponse;
import com.ms_manage_user.application.mapper.UserMapper;
import com.ms_manage_user.domain.model.User;
import com.ms_manage_user.domain.model.UserRol;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.UserRepository;
import com.ms_manage_user.infraestructure.controller.dto.input.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        User user = mapper.toEntity(request);
        return mapper.toResponse(repository.save(user));
    }

    public List<UserResponse> findAll() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            return List.of(); // Devuelve lista vacía sin lanzar excepciones
        }
        return users.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public Optional<UserResponse> findById(UUID id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    /*
    public Optional<UserResponse> updateUser(UUID id, UserRequest request) {
        return repository.findById(id).map(existing -> {
            mapper.updateUserFromRequest(request, existing);
            return mapper.toResponse(repository.save(existing));
        });
    }
    */

    public Optional<UserResponse> updateUser(UUID id, UserRequest request) {
        return repository.findById(id).map(existingUser -> {

            // Mapea todos los campos excepto password
            mapper.updateUserFromRequest(request, existingUser);

            // Solo actualiza el password si viene en el request y no está vacío
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                String encodedPassword = passwordEncoder.encode(request.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            User updated = repository.save(existingUser);
            return mapper.toResponse(updated);
        });
    }



    /*public void deleteUser(UUID id) {
        repository.deleteById(id);
    }*/

    public void deleteUser(UUID userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Validar si el usuario tiene rol ADMIN no se puede eliminar
        if (user.getRole() == UserRol.ADMIN) {
            long count = repository.countByRole(user.getRole());
            if (count <= 1) {
                throw new IllegalStateException("Cannot delete the only user with role " + user.getRole());
            }
        }
        repository.delete(user);
    }

    public void registerUser(RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("The user is already registered.");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("The email is already registered.");
        }
        if (repository.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())) {
            throw new IllegalArgumentException("There is already a user with exactly that first and last name.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        repository.save(user);
    }

    public UserResponse findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Username not found."));
    }


}
