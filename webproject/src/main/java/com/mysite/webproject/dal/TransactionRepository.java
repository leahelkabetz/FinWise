package com.mysite.webproject.dal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

List<Transaction> findByUserIdAndCategory_CategoryId(Long userId, Long categoryId);

List<Transaction> findByUserId(Long userId);


}
