package com.mysite.webproject.service;

import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl implements ReportService {

     @Autowired
     private TransactionRepository transactionRepo;

   @Override
    public Report generateMonthlyReport(Long userId, int month, int year) {
        List<Transaction> transactions = transactionRepo.findByUserId(userId).stream()
                .filter(t -> t.getDate() != null &&
                        t.getDate().getYear() == year &&
                        t.getDate().getMonthValue() == month)
                .toList();

        double totalIncome = transactions.stream()
                .filter(t -> t.getAmount() != null && t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> t.getAmount() != null && t.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Report report = new Report();
        report.setMonth(month);
        report.setYear(year);
        report.setTransactions(transactions);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpenses);
        report.setBalance(totalIncome + totalExpenses);
        return report;
    }

    @Override
    public byte[] generateReportPdf(int month, int year) {
        throw new UnsupportedOperationException("Unimplemented method 'generateReportPdf'");
    }

 
}
