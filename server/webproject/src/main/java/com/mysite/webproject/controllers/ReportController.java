package com.mysite.webproject.controllers;

import com.mysite.webproject.model.Report;
import com.mysite.webproject.service.ReportService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService rs;

 @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdfReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Report report = rs.generateReport(userId, startDate, endDate);
        byte[] pdfBytes = rs.generateReportPdf(report);

        String fileName = "report_" + startDate + "_to_" + endDate + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}


