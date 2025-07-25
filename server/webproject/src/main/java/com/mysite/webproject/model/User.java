package com.mysite.webproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data

public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true, nullable = false)
    private String userNumber;
    @Column
    private String userName;
    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Long phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
}
