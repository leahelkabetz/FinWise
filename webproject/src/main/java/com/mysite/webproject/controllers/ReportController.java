package com.mysite.webproject.controllers;

import com.mysite.webproject.model.Report;
import com.mysite.webproject.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // החזרת אובייקט Report לפי חודש ושנה (JSON רגיל)
    @GetMapping("/getreport/{month}/{year}")
    public Report generateReport(@PathVariable int month, @PathVariable int year) {
        return reportService.generateMonthlyReport(month, year);
    }

    // הורדת הדוח כקובץ PDF
    @GetMapping("/downloadreport/{month}/{year}/pdf")
    public ResponseEntity<byte[]> downloadReportPdf(@PathVariable int month, @PathVariable int year) {
        byte[] pdfContent = reportService.generateReportPdf(month, year);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report_" + month + "_" + year + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
