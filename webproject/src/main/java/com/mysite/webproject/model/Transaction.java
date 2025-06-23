package com.mysite.webproject.model;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Long transactionId;

    @Column
    private Long userNumber;

    @Column
    private String type;

    @Column
    private Category category;

    @Column
   private String description;

    @Column
    private Double amount;

    @Column
    private LocalDate date;

    @Column
    private boolean isFixed;

    @Column
    private boolean isEstimated;

}
