package com.mysite.webproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.model.User;
import com.mysite.webproject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService us;

    @PostMapping("/register")
    public void addUser(@RequestBody User u) {
        us.addUser(u);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User u) {
        boolean success = us.login(u.getEmail(), u.getPassword());
        if (success)
            return ResponseEntity.ok("Login successful");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

}
