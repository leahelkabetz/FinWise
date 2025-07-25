package com.mysite.webproject.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mysite.webproject.model.Alert;

public interface AlertRepository  extends CrudRepository<Alert,Long> {
    List<Alert> findByLevel(String level); 

}
