package com.mysite.webproject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.UserRepository;
import com.mysite.webproject.model.Alert;
import com.mysite.webproject.model.User;

@Service
public class AlertServiceImpl implements AlertService {


    @Autowired
    private UserRepository userRepo;


    public void sendAlert(Alert alert) {
        if (alert.getUser() == null && alert.getUserId() != null) {
            User user = userRepo.findById(alert.getUserId()).orElse(null);
            alert.setUser(user);
        }
}
}