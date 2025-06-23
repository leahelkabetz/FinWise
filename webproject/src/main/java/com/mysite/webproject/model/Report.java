package com.mysite.webproject.model;

import lombok.Data;
import java.util.List;

@Data
public class Report {
    private double totalIncome;
    private double totalExpense;
    private double balance;
    private int month;
    private int year;
    private List<Transaction> transactions;
}
