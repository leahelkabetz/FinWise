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
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUserNumber(updated.getUserNumber());
            user.setEmail(updated.getEmail());
            user.setPassword(updated.getPassword());
            user.setPhoneNumber(updated.getPhoneNumber());

            userRepo.save(user);
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    // @Override
    // public void deleteUser(Long id) {
    //     if (!userRepo.existsById(id)) {
    //         throw new RuntimeException("User with id " + id + " not found");
    //     }
    //     userRepo.deleteById(id);
    // }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty())
            return Optional.empty();

        User user = userOpt.get();
        if (!user.getPassword().equals(password))
            return Optional.empty();
        return Optional.of(user);
    }

    // אולי לא נצרך
    @Override
    public Optional<User> getUserByNumber(Long number) {
        return userRepo.findByUserNumber(number);
    }
}
