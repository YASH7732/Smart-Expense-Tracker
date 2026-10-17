package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void testUnauthenticatedGetExpensesReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testAddExpenseAuthenticated() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 42L);
        session.setAttribute("username", "john");
        session.setAttribute("role", "USER");

        Expense expense = new Expense();
        expense.setTitle("Coffee");
        expense.setCategory("Food");
        expense.setAmount(150.0);

        mockMvc.perform(post("/expenses")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Coffee"))
                .andExpect(jsonPath("$.userId").value(42));
    }

    @Test
    void testAddExpenseValidationFailure() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 42L);

        Expense invalidExpense = new Expense();
        invalidExpense.setTitle("");
        invalidExpense.setCategory("Food");
        invalidExpense.setAmount(50.0);

        mockMvc.perform(post("/expenses")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidExpense)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUserCannotDeleteOthersExpense() throws Exception {
        Expense expense = new Expense();
        expense.setTitle("Books");
        expense.setCategory("Education");
        expense.setAmount(500.0);
        expense.setUserId(99L);
        Expense saved = expenseRepository.save(expense);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 42L);
        session.setAttribute("role", "USER");

        mockMvc.perform(delete("/expenses/" + saved.getId()).session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminCanDeleteAnyExpense() throws Exception {
        Expense expense = new Expense();
        expense.setTitle("Gadget");
        expense.setCategory("Entertainment");
        expense.setAmount(1200.0);
        expense.setUserId(99L);
        Expense saved = expenseRepository.save(expense);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("role", "ADMIN");

        mockMvc.perform(delete("/expenses/" + saved.getId()).session(session))
                .andExpect(status().isOk());
    }
}
