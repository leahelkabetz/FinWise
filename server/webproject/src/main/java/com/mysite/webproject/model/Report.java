package com.mysite.webproject.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class Report {
    private double totalIncome;
    private double totalExpense;
    private double balance;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TransactionDTO> transactions;
}
