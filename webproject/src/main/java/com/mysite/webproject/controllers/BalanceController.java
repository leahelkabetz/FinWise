package com.mysite.webproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Balance;
import com.mysite.webproject.service.BalanceService;


@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private BalanceService bs;

    @GetMapping("/get")
    public Balance getBalance() {
        return bs.getBalance();
    }

    @PutMapping("/update")
    public void updateBalance(@RequestParam double newTotal) {
        bs.updateBalance(newTotal);
    }

    @GetMapping("/final")
    public double calculateFinalBalance() {
        return bs.calculateFinalBalance();
    }
}
