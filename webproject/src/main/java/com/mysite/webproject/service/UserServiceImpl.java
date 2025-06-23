package com.mysite.webproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.UserRepository;
import com.mysite.webproject.model.User;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void addUser(User user) {
        if (!userRepo.findByUserNumber(user.getUserNumber()).isEmpty() ||
                !userRepo.findByEmail(user.getEmail()).isEmpty()) {
            throw new RuntimeException("User already exists");
        }
        userRepo.save(user);
    }

    @Override
    public void updateUser(Long id, User updated) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public Optional<User> getUserByNumber(Long number) {
        return userRepo.findByUserNumber(number);
    }

    @Override
    public boolean login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        return user.getPassword().equals(password);
    }
}
