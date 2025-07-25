package com.mysite.webproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.Alert;
import com.mysite.webproject.service.AlertService;


@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService as;

    @PostMapping("/sendalert")
    public void sendAlert(@RequestBody Alert alert) {
        as.sendAlert(alert);
    }

    @GetMapping("/getall")
    public List<Alert> getAllAlerts() {
        return as.getAllAlerts();
    }

}

