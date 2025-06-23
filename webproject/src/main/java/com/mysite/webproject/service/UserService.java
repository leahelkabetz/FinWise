package com.mysite.webproject.service;

import java.util.Optional;

import com.mysite.webproject.model.User;

public interface UserService {

void addUser(User user);

void updateUser(Long id, User updated);

void deleteUser(Long id);

Optional<User> getUserByNumber(Long number);

boolean login(String email, String password);

}
