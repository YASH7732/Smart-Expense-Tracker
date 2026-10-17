package com.example.demo;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterHashesPasswordAndAssignsUserRole() throws Exception {
        Map<String, String> userPayload = Map.of(
                "username", "testuser",
                "password", "secret123"
        );

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("REGISTERED"));

        User saved = userRepository.findByUsername("testuser").orElseThrow();
        assertNotEquals("secret123", saved.getPassword());
        assertTrue(saved.getPassword().startsWith("$2a$") || saved.getPassword().startsWith("$2b$"));
    }

    @Test
    void testLoginValidCredentials() throws Exception {
        Map<String, String> regPayload = Map.of(
                "username", "loginuser",
                "password", "mypassword"
        );

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regPayload)))
                .andExpect(status().isOk());

        Map<String, String> loginPayload = Map.of(
                "username", "loginuser",
                "password", "mypassword"
        );

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    void testLoginInvalidPasswordReturnsInvalid() throws Exception {
        Map<String, String> regPayload = Map.of(
                "username", "user1",
                "password", "correctpass"
        );

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regPayload)))
                .andExpect(status().isOk());

        Map<String, String> badLoginPayload = Map.of(
                "username", "user1",
                "password", "wrongpass"
        );

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badLoginPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("INVALID"));
    }

    @Test
    void testNormalUserGetsForbiddenOnUsersEndpoint() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 100L);
        session.setAttribute("username", "normaluser");
        session.setAttribute("role", "USER");

        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminUserCanAccessUsersEndpoint() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("username", "admin");
        session.setAttribute("role", "ADMIN");

        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isOk());
    }
}
