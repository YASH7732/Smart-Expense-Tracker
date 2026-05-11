package com.example.demo;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public List<Expense> getAllExpenses(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return List.of();
        return expenseRepository.findByUserId(userId);
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return null;
        expense.setUserId(userId);
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Expense expense = expenseRepository.findById(id).orElseThrow();
        if (!expense.getUserId().equals(userId)) return "UNAUTHORIZED";
        expenseRepository.deleteById(id);
        return "Expense deleted successfully";
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Expense existing = expenseRepository.findById(id).orElseThrow();
        if (!existing.getUserId().equals(userId)) return null;
        existing.setTitle(updatedExpense.getTitle());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        return expenseRepository.save(existing);
    }
}
