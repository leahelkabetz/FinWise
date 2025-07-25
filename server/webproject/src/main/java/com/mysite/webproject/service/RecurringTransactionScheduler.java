package com.mysite.webproject.service;

import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.CategoryRepository;
import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.Category;
import com.mysite.webproject.model.CategoryType;
import com.mysite.webproject.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RecurringTransactionScheduler {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BalanceRepository balanceRepo;

    @Scheduled(cron = "0 0 1 * * *") // כל יום ב־01:00 בלילה
    public void processRecurringTransactions() {
        LocalDate today = LocalDate.now();
        System.out.println("🔁 התחלת עיבוד תנועות קבועות בתאריך: " + today);

        // שליפת כל התנועות הקבועות של היום
        List<Transaction> recurringToday = transactionRepo.findByIsFixedTrueAndDate(today);

        for (Transaction t : recurringToday) {
            try {
                Long userId = t.getUserId();
                double amount = t.getAmount();

                // קבלת קטגוריה כדי לבדוק אם זו הוצאה
                Category category = categoryRepo.findById(t.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("קטגוריה לא קיימת לתנועה ID " + t.getTransactionId()));

                if (category.getType() == CategoryType.EXPENSE) {
                    amount = -Math.abs(amount);
                } else {
                    amount = Math.abs(amount);
                }

                // עדכון היתרה
                Balance balance = balanceRepo.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("לא נמצאה יתרה למשתמש ID " + userId));

                double newBalance = balance.getTotalBalance() + amount;
                balance.setTotalBalance(newBalance);
                balanceRepo.save(balance);

                // סימון שהתנועה בוצעה כדי לא לבצע שוב
                t.setProcessed(false); 
                transactionRepo.save(t);

                System.out.println("✅ תנועה קבועה בוצעה בהצלחה למשתמש " + userId);

            } catch (Exception ex) {
                System.err.println("❌ שגיאה בטיפול בתנועה ID " + t.getTransactionId() + ": " + ex.getMessage());
            }
        }

        System.out.println("✅ סיום עיבוד תנועות קבועות");
    }
}
