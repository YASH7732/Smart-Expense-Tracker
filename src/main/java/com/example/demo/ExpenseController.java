package com.example.demo;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<Expense> getAllExpenses(HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            return List.of();
        }
        if (SessionUtil.isAdmin(session)) {
            return expenseRepository.findAll();
        }
        return expenseRepository.findByUserId(userId);
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        validateExpense(expense);
        expense.setTitle(expense.getTitle().trim());
        expense.setCategory(expense.getCategory().trim());
        expense.setUserId(userId);
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteExpense(@PathVariable Long id, HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (!SessionUtil.isAdmin(session) && !expense.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        expenseRepository.deleteById(id);
        return Map.of("message", "Expense deleted successfully");
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense, HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (!SessionUtil.isAdmin(session) && !existing.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        validateExpense(updatedExpense);
        existing.setTitle(updatedExpense.getTitle().trim());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory().trim());
        return expenseRepository.save(existing);
    }

    private void validateExpense(Expense expense) {
        if (expense == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense body is required");
        }
        if (expense.getTitle() == null || expense.getTitle().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        if (expense.getCategory() == null || expense.getCategory().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }
        if (expense.getAmount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than or equal to 0");
        }
    }
}
