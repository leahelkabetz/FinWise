package com.mysite.webproject.service;

import java.util.Optional;

import com.mysite.webproject.model.User;
import com.mysite.webproject.model.UserDTO;

public interface UserService {

void addUser(User user);      //הוספת משתמש חדש למערכת

void updateUser(Long id, UserDTO updated);

Optional<User> login(String email, String password);  //התחברות
  public Optional<UserDTO> getUserById(Long id);

}
