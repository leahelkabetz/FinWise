package com.mysite.webproject.service;

import com.mysite.webproject.dal.TransactionRepository;
import com.mysite.webproject.model.Report;
import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.model.TransactionDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Table;
import java.time.format.DateTimeFormatter;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionRepository transactionRepo;
    private static final DateTimeFormatter HEBREW_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public Report generateReport(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepo.findByUserId(userId).stream()
                .filter(t -> t.getDate() != null &&
                        !t.getDate().isBefore(startDate) &&
                        !t.getDate().isAfter(endDate))
                .toList();

        double totalIncome = transactions.stream()
                .filter(t -> t.getAmount() != null && t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> t.getAmount() != null && t.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum();

        // המרה מ-Transaction ל-TransactionDTO
        List<TransactionDTO> dtoList = transactions.stream().map(t -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setTransactionId(t.getTransactionId());
            dto.setDate(t.getDate());
            dto.setDescription(t.getDescription());
            dto.setAmount(t.getAmount());
            dto.setCategoryName(t.getCategory() != null ? t.getCategory().getName() : "");
            dto.setCategoryTypeLabel(t.getCategory() != null ? t.getCategory().getType().getLabel() : "");
            dto.setFixed(t.isFixed());
            // dto.setBalance(t.());
            return dto;
        }).toList();

        Report report = new Report();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTransactions(dtoList); // שמירת רשימת ה-DTO
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpenses);
        report.setBalance(totalIncome + totalExpenses);

        return report;
    }

    @Override
    public byte[] generateReportPdf(Report report) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // הגדרת פונט עברי
            String fontPath = "src/main/resources/NotoSansHebrew-VariableFont_wdth,wght.ttf";
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, pdf);

            document.setFont(font).setFontSize(10);

            // הגדרת כיוון כללי לימין
            document.setTextAlignment(TextAlignment.RIGHT);

            // כותרת
            Paragraph title = new Paragraph(fixHebrewText("דו\"ח תנועות לפי טווח תאריכים"))
                    .setFont(font)
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBaseDirection(BaseDirection.RIGHT_TO_LEFT);
            document.add(title);

            document.add(new Paragraph("\n"));

            // פרטים כלליים
            document.add(createRtlParagraph(fixHebrewText("מתאריך: " +
                    fixNumberText(report.getStartDate().format(HEBREW_DATE_FORMAT).toString())), font));
            document.add(createRtlParagraph(fixHebrewText("עד תאריך: " +
                    fixNumberText(report.getEndDate().format(HEBREW_DATE_FORMAT).toString())), font));
            document.add(createRtlParagraph(fixHebrewText("יתרה: " +
                    fixNumberText(String.valueOf(report.getBalance()))), font));

            document.add(new Paragraph("\n"));
            Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 4, 3, 3, 2, 3, 2 }))
                    .setWidth(UnitValue.createPercentValue(100));

            // כותרות טבלה

            table.addHeaderCell(createHeaderCell(fixNumberText("קבוע?"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("תיאור"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("סוג קטגוריה"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("שם קטגוריה"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("סכום"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("תאריך"), font));
            table.addHeaderCell(createHeaderCell(fixHebrewText("מזהה תנועה"), font));

            // שורות טבלה
            for (TransactionDTO t : report.getTransactions()) {

                table.addCell(createCell(t.isFixed() ? fixHebrewText("כן") : fixHebrewText("לא"), font,
                        TextAlignment.CENTER));
                table.addCell(createCell(fixHebrewText(t.getDescription()), font, TextAlignment.RIGHT));
                table.addCell(createCell(fixHebrewText(t.getCategoryTypeLabel()), font, TextAlignment.CENTER));

                table.addCell(createCell(fixHebrewText(t.getCategoryName()), font, TextAlignment.CENTER));

                table.addCell(createCell(
                        t.getAmount() != null ? t.getAmount().toString() : "", font, TextAlignment.CENTER));
                table.addCell(createCell(
                        t.getDate() != null ? t.getDate().format(HEBREW_DATE_FORMAT) : "", font, TextAlignment.CENTER));
                table.addCell(createCell(String.valueOf(t.getTransactionId()), font, TextAlignment.CENTER));

            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("שגיאה ביצירת PDF", e);
        }
    }

    private Paragraph createRtlParagraph(String text, PdfFont font) {
        return new Paragraph(text)
                .setFont(font)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBaseDirection(BaseDirection.RIGHT_TO_LEFT);
    }

    // תא טבלה רגיל
    private Cell createCell(String text, PdfFont font, TextAlignment align) {
        return new Cell().add(new Paragraph(text != null ? text : "")
                .setFont(font)
                .setTextAlignment(align)
                .setBaseDirection(BaseDirection.RIGHT_TO_LEFT));
    }

    // תא טבלה כותרת
    private Cell createHeaderCell(String text, PdfFont font) {
        return new Cell().add(new Paragraph(text)
                .setFont(font)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBaseDirection(BaseDirection.RIGHT_TO_LEFT));
    }

    // כדי שהעברית לא יצא הפוך
    private String fixHebrewText(String text) {
        if (text == null || text.isBlank())
            return "";

        if (!text.matches(".*[\\u0590-\\u05FF].*")) {
            return text;
        }

        if (text.matches("^[\\u0590-\\u05FF\\s\\d\"':,().\\-]+$")) {
            return new StringBuilder(text).reverse().toString();
        }

        return text;
    }

    private String fixNumberText(String text) {
        if (text == null || text.isBlank())
            return "";

        return new StringBuilder(text).reverse().toString();
    }

}
