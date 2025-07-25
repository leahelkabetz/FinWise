package com.mysite.webproject.service;

import java.time.LocalDate;
import java.util.List;

import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.model.TransactionDTO;

public interface TransactionService {

void addTransaction(Long userId,Transaction transaction);

Transaction updateTransaction(Long id,Long userId, Transaction updated);

void deleteTransaction(Long id);

List<TransactionDTO> getAllTransactionsByUser(Long userId);

List<TransactionDTO> getTransactionsByDateRange(Long userId, LocalDate start, LocalDate end);

List<Transaction> getTransactionsByCategory(Long userId, Long categoryId);

double calculateTotalBalance(Long userId);

boolean checkForAlerts(Long userId);

 List<TransactionDTO> getFixedTransactions(Long userId);


} 
