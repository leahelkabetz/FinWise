package com.mysite.webproject.service;

import com.mysite.webproject.model.Report;


public interface ReportService {
    Report generateMonthlyReport(int month, int year);
    byte[] generateReportPdf(int month, int year); // להורדת דוח בפורמט PDF
}

