package com.ms_manage_user.infraestructure.controller;

import com.ms_manage_user.application.dto.input.UserRequest;
import com.ms_manage_user.application.service.UserService;
import com.ms_manage_user.application.dto.output.UserResponse;
import com.ms_manage_user.domain.model.User;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.UserRepository;
import com.ms_manage_user.infraestructure.controller.dto.input.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService service;
    private final UserRepository repository;

    public UserController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }


    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crear un nuevo usuario.")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        service.registerUser(request); // este método codifica la contraseña y guardar el usuario
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered user");
    }

    @GetMapping
    @Operation(summary = "Obener todos los usuarios", description = "Lista todos los usuarios registrados")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = service.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "message", "No hay usuarios registrados",
                    "usuarios", List.of()
            ));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario buscando por el ID.")
    public ResponseEntity<User> getUsersById(@PathVariable UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con el ID: " + id));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/enabled")
    @Operation(summary = "Enabled user", description = "Habilita o deshabilita un usuario del sistema por ID")
    public ResponseEntity<String> updateUserEnabledStatus(@PathVariable UUID id, @RequestBody Map<String, Boolean> request) {
        Boolean enabled = request.get("enabled");
        if (enabled == null) {
            return ResponseEntity.badRequest().body("Field 'enabled' is required in request body.");
        }
        return repository.findById(id)
                .map(user -> {
                    user.setEnabled(enabled);
                    repository.save(user);
                    String estado = enabled ? "enabled" : "disabled";
                    return ResponseEntity.ok("User " + user.getUsername() + " has been " + estado + " successfully.");
                })
                .orElse(ResponseEntity.status(404).body("User not found with ID: " + id));
    }




    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un user", description = "Actualizar los datos del usuario por ID")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UserRequest request) {
        return service.updateUser(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con el ID: " + id));

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente.")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " was not found.");
        }
    }

}
