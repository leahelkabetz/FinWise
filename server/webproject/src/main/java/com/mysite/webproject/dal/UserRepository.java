package com.mysite.webproject.dal;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    Optional<User> findByUserNumber(String number);
    Optional<User> findByEmail(String email);
Optional <User> findById(Long id); // This method is already provided by CrudRepository, but can be explicitly defined if needed
    // Other methods can be added as needed
}
