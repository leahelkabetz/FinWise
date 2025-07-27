package com.mysite.webproject.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.model.TransactionDTO;
import com.mysite.webproject.service.TransactionService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService ts;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(
            @RequestParam("userId") Long userId,
            @RequestBody Transaction transaction) {
        ts.addTransaction(userId, transaction);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestParam("userId") Long userId,
            @RequestBody Transaction transaction) {
        Transaction updated = ts.updateTransaction(id, userId, transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        ts.deleteTransaction(id); 
        return ResponseEntity.noContent().build();
    }
@GetMapping("/recurring")
public ResponseEntity<List<TransactionDTO>> getRecurringTransactions(@RequestParam("userId") Long userId) {
    List<TransactionDTO> recurring = ts.getFixedTransactions(userId);
    return ResponseEntity.ok(recurring);
}

    @GetMapping("/get/{userId}")
    public List<TransactionDTO> getAllTransactions(@PathVariable Long userId) {
        return ts.getAllTransactionsByUser(userId);
    }

    @GetMapping("/get-range")
    public List<TransactionDTO> getTransactionsByRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ts.getTransactionsByDateRange(userId, start, end);
    }

    @GetMapping("/bycategory/{categoryId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(
            @RequestParam("userId") Long userId,
            @PathVariable Long categoryId) {
        List<Transaction> transactions = ts.getTransactionsByCategory(userId, categoryId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getTotalBalance(
            @RequestParam("userId") Long userId) {
        double balance = ts.calculateTotalBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/check-alerts")
    public ResponseEntity<Boolean> checkAlerts(
            @RequestParam("userId") Long userId) {
        boolean alert = ts.checkForAlerts(userId);
        return ResponseEntity.ok(alert);
    }

}
