package com.mysite.webproject.dal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    
}
