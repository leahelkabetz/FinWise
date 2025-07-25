package com.mysite.webproject.service;

import com.mysite.webproject.model.Balance;

public interface BalanceService {
    Balance getBalanceByUser(Long userId);

    //void updateBalance(Long userId, double newTotal);
    // double calculateFinalBalance(); // Uncomment if needed for final balance
    // calculation
}
