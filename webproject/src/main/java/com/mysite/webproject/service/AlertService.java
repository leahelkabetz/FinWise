package com.mysite.webproject.service;

import java.util.List;


import com.mysite.webproject.model.Alert;

public interface AlertService {
    void sendAlert(Alert alert);         
    List<Alert> getAllAlerts();                          
    List<Alert> getAlertsByLevel(String level);           
    void deleteAlert(Long id); 
}
