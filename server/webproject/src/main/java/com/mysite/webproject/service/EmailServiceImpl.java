package com.mysite.webproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mysite.webproject.model.Alert;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.CategoryType;
import com.mysite.webproject.model.Transaction;
import com.mysite.webproject.model.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private BalanceService balanceService;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAlertEmail(Alert alert) {
        if (alert.getUser() == null || alert.getUser().getEmail() == null) {
            System.out.println("לא ניתן לשלוח מייל – למשתמש אין כתובת מייל.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String toEmail = alert.getUser().getEmail();

            helper.setFrom(String.format("FinWise ניהול חכם <%s>", fromEmail));
            helper.setTo(toEmail);
            helper.setSubject("📢 התראה חדשה: ");

            User user = alert.getUser();
            Transaction transaction = alert.getTransaction();
            Balance balance = balanceService.getBalanceByUser(user.getUserId());
            String categoryTypeHebrew = transaction.getCategory().getType() == CategoryType.EXPENSE ? "הוצאה" : "הכנסה";

            String htmlContent = "<div style='font-family: Arial, sans-serif; direction: rtl;'>\n" +
                    "  <h2 style='color: #0b7285;'>עדכון בחשבון שלך</h2>\n" +
                    "  <p><strong>שלום " + user.getUserName() + " ,</strong></p>\n" +
                    "  <p><strong>תיאור התנועה:</strong> " + transaction.getDescription() + "</p>\n" +
                    "  <p><strong>קטגוריה:</strong> " + transaction.getCategory().getName() + " - " + categoryTypeHebrew
                    + "</p>\n" +
                    "  <p><strong>סכום:</strong> " + transaction.getAmount() + "</p>\n" +
                    "  <p><strong>יתרה נוכחית:</strong> " + balance.getTotalBalance() + "</p>\n" +
                    (balance.getTotalBalance() >= 0
                            ? "  <p style='color: green; font-weight: bold;'>👍 כל הכבוד! אתה באיזון חיובי</p>\n"
                            : "  <p style='color: red; font-weight: bold;'>⚠️ שים לב: היתרה שלך שלילית</p>\n")
                    +
                    "</div>";
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("מייל נשלח בהצלחה ל-" + toEmail);

        } catch (MessagingException e) {
            System.err.println("שגיאה בשליחת מייל: " + e.getMessage());
        }
    }

}
