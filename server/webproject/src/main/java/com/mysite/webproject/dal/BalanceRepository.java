package com.mysite.webproject.dal;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.Balance;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {
    Optional<Balance> findByUserUserId(Long userId);

    Optional<Balance> findByUserId(Long userId);

}
