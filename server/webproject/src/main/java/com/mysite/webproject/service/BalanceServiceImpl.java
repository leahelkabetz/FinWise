package com.mysite.webproject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysite.webproject.dal.BalanceRepository;
import com.mysite.webproject.model.Balance;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepo;

    

    @Override
    public Balance getBalanceByUser(Long userId) {
        return balanceRepo.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Balance not found for user " + userId));
    }

}
