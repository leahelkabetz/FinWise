package com.mysite.webproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.service.TransactionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService ts;

    @PostMapping("/add")
    public void addTransaction(@RequestBody Transaction t) {
        ts.addTransaction(t);
    }

    @GetMapping("/getAll")
    public List<Transaction> getAllTransactions() {
        return ts.getAllTransactions();
    }

    @PutMapping("/update/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction updated) {
        return ts.updateTransaction(id, updated);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        ts.deleteTransaction(id);
    }
    
    //עדיף לעשות עם הסימני שאלה
    @GetMapping("/getNames/{cName}")
    public List<String> getAllTransactionByCategoryName(@PathVariable String cName) {
        return ts.getAllTransactionCategoryName(cName);
    }

}
