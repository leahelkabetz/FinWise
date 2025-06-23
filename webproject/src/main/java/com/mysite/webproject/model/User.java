package com.mysite.webproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data

public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @Column
    private Long userNumber;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Long phoneNumber;
}
