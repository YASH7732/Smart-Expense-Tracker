package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    private final UserRepository userRepository;

    public AdminBootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        try {
            userRepository.findAll().forEach(user -> {
                if (user.getRole() == null || user.getRole().isBlank()) {
                    user.setRole("USER");
                    userRepository.save(user);
                }
            });

            userRepository.findByUsername(adminUsername).ifPresentOrElse(
                    user -> {
                        user.setRole("ADMIN");
                        user.setPassword(adminPassword);
                        userRepository.save(user);
                    },
                    () -> {
                        User admin = new User();
                        admin.setUsername(adminUsername);
                        admin.setPassword(adminPassword);
                        admin.setRole("ADMIN");
                        userRepository.save(admin);
                    }
            );
        } catch (Exception ex) {
            System.err.println("Admin bootstrap skipped: " + ex.getMessage());
        }
    }
}
