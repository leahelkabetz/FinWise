package com.mysite.webproject.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Alert {

    @Id
    @GeneratedValue
    private Long alertId;

    @Column
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "transactionId")
    private Transaction transaction; 

    @Column
    private String message;

    @Column
    private LocalDateTime timestamp;
}
