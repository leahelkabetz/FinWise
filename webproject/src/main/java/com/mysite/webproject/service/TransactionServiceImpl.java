package com.mysite.webproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public void addTransaction(Transaction transaction) {
        // if (transaction.getTransactionId() != null && transactionRepo.existsById(transaction.getTransactionId()))
        //     throw new RuntimeException("id already exists!!");
        transactionRepo.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction updated) {
        Transaction existing = transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setDescription(updated.getDescription());
        existing.setAmount(updated.getAmount());
        existing.setDate(updated.getDate());
        existing.setCategoryId(updated.getCategoryId());
        existing.setFixed(updated.isFixed());
        existing.setEstimated(updated.isEstimated());
        return transactionRepo.save(existing);
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepo.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepo.deleteById(id);
    }

    @Override
    public List<Transaction> getAllTransactionsByUser(Long userId) {
        return transactionRepo.findByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(Long userId, LocalDate start, LocalDate end) {
        return transactionRepo.findByUserIdAndDateBetween(userId, start, end);
    }

    @Override
    public List<Transaction> getTransactionsByCategory(Long userId, Long categoryId) {
        return transactionRepo.findByUserIdAndCategory_CategoryId(userId, categoryId);
    }

    @Override
    public double calculateTotalBalance(Long userId) {
        return transactionRepo.findByUserId(userId).stream()
                .mapToDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                .sum();
    }

    @Override
    public boolean checkForAlerts(Long userId) {
        double balance = calculateTotalBalance(userId);
        return balance > 0;
    }


}
