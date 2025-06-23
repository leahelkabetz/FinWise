package com.mysite.webproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepo;

    // יש לשנות את המימוש בהתאם למה שאני צריכה בפרויקט שלי
    @Override
    public void addTransaction(Transaction transaction) {
       if(transactionRepo.existsById(transaction.getTransactionId()))
          throw new RuntimeException("id already exists!!");
       transactionRepo.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction updated) {
        throw new UnsupportedOperationException("Unimplemented method 'updateTransaction'");
    }

    @Override
    public void deleteTransaction(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteTransaction'");
    }

    @Override
    public List<Transaction> getAllTransactions() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllTransactions'");
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) {
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionsByDateRange'");
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionsByType'");
    }

    @Override
    public double calculateTotalBalance() {
        throw new UnsupportedOperationException("Unimplemented method 'calculateTotalBalance'");
    }

    @Override
    public void checkForAlerts() {
        throw new UnsupportedOperationException("Unimplemented method 'checkForAlerts'");
    }

    @Override
    public Report generateMonthlyReport(int month, int year) {
        throw new UnsupportedOperationException("Unimplemented method 'generateMonthlyReport'");
    }

}
