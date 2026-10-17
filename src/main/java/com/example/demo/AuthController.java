package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    public AuthController(UserRepository userRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {
            return "INVALID_INPUT";
        }

        if (adminUsername.equalsIgnoreCase(user.getUsername().trim())) {
            return "USERNAME_RESERVED";
        }

        Optional<User> existing = userRepository.findByUsername(user.getUsername().trim());
        if (existing.isPresent()) {
            return "USERNAME_TAKEN";
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return "REGISTERED";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return "INVALID";
        }

        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isEmpty()) {
            return "INVALID";
        }

        User dbUser = found.get();
        boolean passwordMatches = false;
        String storedPassword = dbUser.getPassword();

        if (storedPassword != null && (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$"))) {
            passwordMatches = passwordEncoder.matches(user.getPassword(), storedPassword);
        } else if (storedPassword != null && storedPassword.equals(user.getPassword())) {
            // Backward-compatible migration for existing plaintext passwords
            passwordMatches = true;
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(dbUser);
        }

        if (passwordMatches) {
            String role = dbUser.getRole();
            if (role == null || role.isBlank()) {
                role = "USER";
                dbUser.setRole(role);
                userRepository.save(dbUser);
            }

            session.setAttribute("userId", dbUser.getId());
            session.setAttribute("username", dbUser.getUsername());
            session.setAttribute("role", role);
            return "OK";
        }

        return "INVALID";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "LOGGED_OUT";
    }

    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        String username = (String) session.getAttribute("username");
        String role = SessionUtil.getRole(session);

        if (userId == null) {
            return Map.of("loggedIn", false);
        }

        return Map.of(
                "loggedIn", true,
                "userId", userId,
                "username", username,
                "role", role
        );
    }

    @GetMapping("/admin")
    public String adminPanel(HttpSession session) {
        if (!SessionUtil.isAdmin(session)) {
            return "ACCESS_DENIED";
        }
        return "WELCOME_ADMIN";
    }

    @GetMapping("/users")
    public List<User> users(HttpSession session) {
        if (!SessionUtil.isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin access required");
        }
        return userRepository.findAll();
    }
}
