package com.mysite.webproject.service;

import com.mysite.webproject.model.Alert;

public interface EmailService {

    void sendAlertEmail(Alert alert);
}
