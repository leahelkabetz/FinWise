package com.mysite.webproject.service;

import com.mysite.webproject.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendAlertEmail(Alert alert) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("target@example.com"); // כתובת היעד בפועל
        message.setSubject("התראה חדשה (" + alert.getLevel() + ")");
        message.setText(
                "מספר משתמש: " + alert.getUserNumber() + "\n" +
                "רמת התראה: " + alert.getLevel() + "\n" +
                "תאריך ושעה: " + alert.getTimestamp() + "\n\n" +
                "תוכן ההתראה:\n" + alert.getMessage()
        );
        mailSender.send(message);
    }
}
