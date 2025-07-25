package com.ms_manage_user.infraestructure.config;


import com.ms_manage_user.domain.model.User;
import com.ms_manage_user.infraestructure.adapter.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static com.ms_manage_user.domain.model.UserRol.ADMIN;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setFirstName("Admin");
                admin.setLastName(" del sistema");
                admin.setEnabled(true);
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // ¡Recuerda que debe estar encriptado!
                admin.setRole(ADMIN);
                userRepository.save(admin);
                System.out.println(" Usuario " +"´ admin ´" +" creado correctamente y su contraseña "+"´ Admin123*-+ ´" +"debe cambiarla por motivo de seguridad.");
            }
        };
    }
}