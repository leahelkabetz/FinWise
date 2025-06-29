package com.mysite.webproject.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.service.ReportService;
import com.mysite.webproject.service.TransactionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService ts;
    @Autowired
    private ReportService rs;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        ts.addTransaction(transaction);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {
        Transaction updated = ts.updateTransaction(id, transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        ts.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = ts.getAllTransactionsByUser(userId);
        return ResponseEntity.ok(transactions);
    }

   @GetMapping("/range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam("userId") Long userId,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
    

        List<Transaction> transactions = ts.getTransactionsByDateRange(
                userId,
                LocalDate.parse(startDate.trim()),
                LocalDate.parse(endDate.trim()));
        return ResponseEntity.ok(transactions);
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
     @GetMapping("/report")
    public ResponseEntity<Report> getMonthlyReport(
            @RequestParam("userId") Long userId,
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        Report report = rs.generateMonthlyReport(userId, month, year);
        return ResponseEntity.ok(report);
    }

}
