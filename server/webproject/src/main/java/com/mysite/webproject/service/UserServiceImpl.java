package com.mysite.webproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.dal.UserRepository;
import com.mysite.webproject.model.Balance;
import com.mysite.webproject.model.User;
import com.mysite.webproject.model.UserDTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BalanceRepository balanceRepo;

    @Override
    public void addUser(User user) {
        if (!userRepo.findByUserNumber(user.getUserNumber()).isEmpty() ||
                !userRepo.findByEmail(user.getEmail()).isEmpty()) {
            throw new RuntimeException("User already exists");
        }
        User savedUser = userRepo.save(user);
        Balance balance = new Balance();
        balance.setUserId(savedUser.getUserId());
        balance.setTotalBalance(0.0);

        balanceRepo.save(balance);
    }

   @Override
public void updateUser(Long id, UserDTO updated) {
    Optional<User> userOpt = userRepo.findById(id);
    if (userOpt.isPresent()) {
        User user = userOpt.get();

        // שומרים את userNumber הקיים, לא מעדכנים אותו
        // user.setUserNumber(user.getUserNumber());
user.setUserName(updated.getUserName());
        user.setEmail(updated.getEmail());
        user.setPassword(updated.getPassword());
        user.setPhoneNumber(updated.getPhoneNumber());

        userRepo.save(user);
    } else {
        throw new RuntimeException("User with id " + id + " not found");
    }
}


    

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

    @Override
public Optional<UserDTO> getUserById(Long id) {
    return userRepo.findById(id).map(user -> {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    });
}


}
