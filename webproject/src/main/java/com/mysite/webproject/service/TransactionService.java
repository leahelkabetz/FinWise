package com.mysite.webproject.service;

import java.time.LocalDate;
import java.util.List;

import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;

public interface TransactionService {

void addTransaction(Transaction transaction);

Transaction updateTransaction(Long id, Transaction updated);

void deleteTransaction(Long id);

List<Transaction> getAllTransactions();

List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end);

List<Transaction> getTransactionsByType(String type); // "income" or "expense"

double calculateTotalBalance();

void checkForAlerts();

Report generateMonthlyReport(int month, int year);

List<String> getAllTransactionCategoryName(String categoryName);
} 
