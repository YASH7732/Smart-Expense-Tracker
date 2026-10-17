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
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AdminBootstrap(UserRepository userRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

            String encodedAdminPassword = passwordEncoder.encode(adminPassword);

            userRepository.findByUsername(adminUsername).ifPresentOrElse(
                    user -> {
                        user.setRole("ADMIN");
                        String current = user.getPassword();
                        if (current == null || !(current.startsWith("$2a$") || current.startsWith("$2b$") || current.startsWith("$2y$"))) {
                            user.setPassword(encodedAdminPassword);
                        }
                        userRepository.save(user);
                    },
                    () -> {
                        User admin = new User();
                        admin.setUsername(adminUsername);
                        admin.setPassword(encodedAdminPassword);
                        admin.setRole("ADMIN");
                        userRepository.save(admin);
                    }
            );
        } catch (Exception ex) {
            System.err.println("Admin bootstrap skipped: " + ex.getMessage());
        }
    }
}
