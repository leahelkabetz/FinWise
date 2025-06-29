package com.mysite.webproject.service;

import java.time.LocalDate;
import java.util.List;

import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;

public interface TransactionService {

void addTransaction(Transaction transaction);

Transaction updateTransaction(Long id, Transaction updated);

void deleteTransaction(Long id);

List<Transaction> getAllTransactionsByUser(Long userId);

List<Transaction> getTransactionsByDateRange(Long userId, LocalDate start, LocalDate end);

List<Transaction> getTransactionsByCategory(Long userId, Long categoryId);

double calculateTotalBalance(Long userId);

boolean checkForAlerts(Long userId);

} 
