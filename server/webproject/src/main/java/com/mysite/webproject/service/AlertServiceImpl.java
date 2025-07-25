package com.mysite.webproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.AlertRepository;
import com.mysite.webproject.model.Alert;

@Service

public class AlertServiceImpl implements AlertService {
    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private EmailService emailService;

    public void sendAlert(Alert alert) {
        alertRepo.save(alert);
        emailService.sendAlertEmail(alert);
    }

    @Override
    public List<Alert> getAllAlerts() {
        List<Alert> alerts = new ArrayList<>();
        alertRepo.findAll().forEach(alerts::add);
        return alerts;
    }

    @Override
    public List<Alert> getAlertsByLevel(String level) {
        return alertRepo.findByLevel(level);
    }

    @Override
    public void deleteAlert(Long id) {
        alertRepo.deleteById(id);
    }
}
