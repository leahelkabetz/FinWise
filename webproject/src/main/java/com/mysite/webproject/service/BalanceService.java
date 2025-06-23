package com.mysite.webproject.service;

import com.mysite.webproject.model.Balance;

public interface BalanceService {
    Balance getBalance();
    void updateBalance(double newTotal);
    double calculateFinalBalance();
}
