package com.mysite.webproject.service;

import com.mysite.webproject.dal.AlertRepository;
import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.CategoryRepository;
import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Alert;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.Category;
import com.mysite.webproject.model.CategoryType;
import com.mysite.webproject.model.Transaction;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RecurringTransactionScheduler {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BalanceRepository balanceRepo;

    @Autowired
    private AlertService alertService;
    @Autowired
    private AlertRepository alertRepo;

   @Scheduled(cron = "0 0 1 1 * *") 
@Transactional
public void processRecurringTransactions() {
    LocalDate today = LocalDate.now();

    List<Transaction> recurringTransactions = transactionRepo.findByIsFixedTrue();

    for (Transaction t : recurringTransactions) {
        try {
            LocalDate startDate = t.getDate(); 
            LocalDate lastRun = t.getLastProcessedDate(); 
            boolean shouldRunThisMonth = false;

            if (lastRun == null && !startDate.isAfter(today)) {
                shouldRunThisMonth = true;
            } else if (lastRun != null && lastRun.plusMonths(1).isBefore(today.plusDays(1))) {
                shouldRunThisMonth = true;
            }

            if (!shouldRunThisMonth) continue;

            Transaction newTransaction = new Transaction();
            newTransaction.setUserId(t.getUserId());
            newTransaction.setCategoryId(t.getCategoryId());
            newTransaction.setAmount(t.getAmount());
            newTransaction.setDescription(t.getDescription());
            newTransaction.setDate(today);
            newTransaction.setFixed(false); 
            transactionRepo.save(newTransaction);

            double amount = newTransaction.getAmount();
            Category category = categoryRepo.findById(newTransaction.getCategoryId())
                .orElseThrow(() -> new RuntimeException("קטגוריה לא קיימת"));

            if (category.getType() == CategoryType.EXPENSE) {
                amount = -Math.abs(amount);
            }

            Balance balance = balanceRepo.findByUserId(t.getUserId())
                .orElseThrow(() -> new RuntimeException("Balance not found"));

            balance.setTotalBalance(balance.getTotalBalance() + amount);
            balanceRepo.save(balance);

            t.setLastProcessedDate(today);
            transactionRepo.save(t);

            Alert alert = new Alert();
            alert.setUserId(t.getUserId());
            alert.setTransaction(newTransaction);
            alert.setMessage("תנועה חודשית קבועה התבצעה");
            alert.setTimestamp(LocalDateTime.now());
            alertRepo.save(alert);
            alertService.sendAlert(alert);

            System.out.println("🔁 נוצרה תנועה חודשית למשתמש " + t.getUserId());

        } catch (Exception e) {
            System.err.println("⚠️ שגיאה בטיפול בתנועה קבועה: " + e.getMessage());
        }
    }

    System.out.println("✅ הסתיים עיבוד חודשי של תנועות קבועות");
}

}
