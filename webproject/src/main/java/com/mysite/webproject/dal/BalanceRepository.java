package com.mysite.webproject.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.Balance;

@Repository
public interface BalanceRepository extends CrudRepository<Balance,Long> {
    
}
