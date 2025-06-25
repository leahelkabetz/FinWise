package com.mysite.webproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Balance;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateFinalBalance'");
    }


}
