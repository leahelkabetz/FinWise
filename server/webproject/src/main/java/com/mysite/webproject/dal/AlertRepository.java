package com.mysite.webproject.dal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mysite.webproject.model.Alert;

public interface AlertRepository  extends CrudRepository<Alert,Long> {
    List<Alert> findByUserId(Long userId); 
    List<Alert> findByUserIdAndTimestampAfter(Long userId, LocalDateTime from); 

}
