package com.mysite.webproject.dal;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    Optional<User> findByUserNumber(Long number);
    Optional<User> findByEmail(String email);

}
