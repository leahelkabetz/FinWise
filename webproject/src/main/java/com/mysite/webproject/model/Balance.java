package com.mysite.webproject.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Balance {
   @Id
   @GeneratedValue
    private Long balanceId;

    @Column
    private Long userNumber;

    @Column
    private Double totalBalance;
}
