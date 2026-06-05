package com.example.demo;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

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
            return null;
        }
        expense.setUserId(userId);
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id, HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            return "UNAUTHORIZED";
        }

        Expense expense = expenseRepository.findById(id).orElseThrow();
        if (!SessionUtil.isAdmin(session) && !expense.getUserId().equals(userId)) {
            return "UNAUTHORIZED";
        }

        expenseRepository.deleteById(id);
        return "Expense deleted successfully";
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense, HttpSession session) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            return null;
        }

        Expense existing = expenseRepository.findById(id).orElseThrow();
        if (!SessionUtil.isAdmin(session) && !existing.getUserId().equals(userId)) {
            return null;
        }

        existing.setTitle(updatedExpense.getTitle());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        return expenseRepository.save(existing);
    }
}
