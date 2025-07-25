package com.mysite.webproject.service;

import java.time.LocalDate;

import com.mysite.webproject.model.Report;


public interface ReportService {
    byte[] generateReportPdf(Report report); // להורדת דוח בפורמט PDF
    Report generateReport(Long userId, LocalDate startDate, LocalDate endDate);

}

