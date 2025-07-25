package com.mysite.webproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Balance;
import com.mysite.webproject.service.BalanceService;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService bs;

    @GetMapping("/byuser/{userId}")
    public Balance getBalanceByUser(@PathVariable Long userId) {
        return bs.getBalanceByUser(userId);
    }

}
