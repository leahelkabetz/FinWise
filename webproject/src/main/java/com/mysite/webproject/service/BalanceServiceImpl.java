package com.mysite.webproject.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.Transaction;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    //החזרת היתרה
    @Override
    public Balance getBalance() {
        return balanceRepo.findById(1L)
            .orElseThrow(() -> new RuntimeException("Balance not found"));
    }
   //עדכון ראשוני- חד פעמי
    @Override
    public void updateBalance(double newTotal) {
        Balance balance = getBalance();
        balance.setTotalBalance(newTotal);
        balanceRepo.save(balance);
    }
   //קבלת יתרה נוכחית
    @Override
    public double calculateFinalBalance() {
        Balance balance = getBalance();
        List<Transaction> transactions = StreamSupport
                .stream(transactionRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        double income = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("income"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return balance.getTotalBalance() + income - expense;
    }

}
