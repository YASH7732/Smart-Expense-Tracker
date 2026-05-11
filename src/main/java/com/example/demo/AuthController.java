package com.example.demo;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            return "USERNAME_TAKEN";
        }
        userRepository.save(user);
        return "REGISTERED";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            session.setAttribute("userId", found.get().getId());
            session.setAttribute("username", found.get().getUsername());
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
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        if (userId == null) {
            return Map.of("loggedIn", false);
        }
        return Map.of("loggedIn", true, "username", username, "userId", userId);
    }
}
