package com.mysite.webproject.service;

import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl implements ReportService {

     @Autowired
     private TransactionRepository transactionRepo;

    @Override
    public Report generateMonthlyReport(int month, int year) {
        throw new UnsupportedOperationException("Unimplemented method 'generateMonthlyReport'");
    }

    @Override
    public byte[] generateReportPdf(int month, int year) {
        throw new UnsupportedOperationException("Unimplemented method 'generateReportPdf'");
    }

 
}
