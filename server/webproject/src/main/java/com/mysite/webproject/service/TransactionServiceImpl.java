package com.mysite.webproject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.AlertRepository;
import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.CategoryRepository;
import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Alert;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.Category;
import com.mysite.webproject.model.CategoryType;
import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.model.TransactionDTO;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private BalanceRepository balanceRepo;
    @Autowired
    private AlertRepository alertRepo;
    @Autowired
    private AlertService alertService;

    @Override
    public Transaction updateTransaction(Long id, Long userId, Transaction updated) {
        Transaction existing = transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized update attempt");
        }

        existing.setDescription(updated.getDescription());
        existing.setAmount(updated.getAmount());
        existing.setDate(updated.getDate());
        existing.setCategoryId(updated.getCategoryId());
        existing.setFixed(updated.isFixed());
        return transactionRepo.save(existing);
    }

    @Override
    public void addTransaction(Long userId, Transaction transaction) {
        if (!transaction.isFixed()) {
            transaction.setUserId(userId);

            Category category = categoryRepo.findById(transaction.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            transaction.setCategory(category); // ✅ תיקון קריטי!
            System.out.println("✅ קטגוריה שנמצאה: " + category.getName() + " (" + category.getType() + ")");

            transactionRepo.save(transaction);

            Balance balance = balanceRepo.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Balance not found"));
            System.out.println("🔹 יתרה לפני עדכון: " + balance.getTotalBalance());

            double amount = transaction.getAmount();
            if (category.getType() == CategoryType.EXPENSE) {
                amount = -amount;
            }
            System.out.println("סכום חתום (לפי סוג פעולה): " + amount);

            balance.setTotalBalance(balance.getTotalBalance() + amount);
            System.out.println("יתרה חדשה: " + balance.getTotalBalance());

            balanceRepo.save(balance);
            System.out.println("balance נשמר בהצלחה");

            Alert alert = new Alert();
            alert.setUserId(userId);
            alert.setTransaction(transaction);
            alert.setMessage("תנועה חדשה התבצעה");
            alert.setTimestamp(LocalDateTime.now());
            alertRepo.save(alert);

            alertService.sendAlert(alert); 
            System.out.println("התראה נשלחה למשתמש " + userId);

        } else {
            // אם הפעולה היא קבועה, לא נעדכן את היתרה
            transaction.setUserId(userId);
            transactionRepo.save(transaction);
            System.out.println("נשמרה תנועה קבועה לביצוע עתידי בתאריך " + transaction.getDate());
        }
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepo.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepo.deleteById(id);

    }

    @Override
    public List<TransactionDTO> getAllTransactionsByUser(Long userId) {
        List<Transaction> transactions = transactionRepo.findByUserIdOrderByDateAsc(userId);

        List<TransactionDTO> result = new ArrayList<>();
        double balance = 0;

        for (Transaction t : transactions) {
            Category category = t.getCategory();

            double signedAmount = category.getType() == CategoryType.INCOME ? t.getAmount() : -t.getAmount();
            balance += signedAmount;

            TransactionDTO dto = new TransactionDTO();
            dto.setDate(t.getDate());
            dto.setDescription(t.getDescription());
            dto.setAmount(t.getAmount());
            dto.setCategoryName(category.getName());
            dto.setCategoryTypeLabel(category.getType().getLabel()); 
            dto.setBalance(balance);

            result.add(dto);
        }

        return result;
    }

    @Override
    public List<TransactionDTO> getTransactionsByDateRange(Long userId, LocalDate start, LocalDate end) {
        List<Transaction> transactions = transactionRepo.findByUserIdAndDateBetweenOrderByDateAsc(userId, start, end);

        List<TransactionDTO> result = new ArrayList<>();
        double balance = 0;

        for (Transaction t : transactions) {
            Category category = t.getCategory();

            double signedAmount = category.getType() == CategoryType.INCOME
                    ? t.getAmount()
                    : -t.getAmount();
            balance += signedAmount;

            TransactionDTO dto = new TransactionDTO();
            dto.setDate(t.getDate());
            dto.setDescription(t.getDescription());
            dto.setAmount(t.getAmount());
            dto.setCategoryName(category.getName());
            dto.setFixed(t.isFixed());
            dto.setCategoryTypeLabel(category.getType().getLabel());
            dto.setBalance(balance);

            result.add(dto);
        }

        return result;
    }

    @Override
    public List<TransactionDTO> getFixedTransactions(Long userId) {
        List<Transaction> fixedTransactions = transactionRepo.findByUserIdAndIsFixedTrue(userId);

        List<TransactionDTO> result = new ArrayList<>();
        for (Transaction t : fixedTransactions) {
            TransactionDTO dto = new TransactionDTO();
            dto.setTransactionId(t.getTransactionId());
            dto.setDate(t.getDate());
            dto.setDescription(t.getDescription());
            dto.setAmount(t.getAmount());
            dto.setFixed(t.isFixed());
            dto.setCategoryName(t.getCategory().getName());
            dto.setCategoryTypeLabel(t.getCategory().getType().getLabel());
            dto.setBalance(0.0); 

            result.add(dto);
        }

        return result;
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
