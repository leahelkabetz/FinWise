package com.mysite.webproject.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Transaction {
    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(
    name = "transaction_seq",
    sequenceName = "TRANSACTION_SEQ",
    allocationSize = 1
)
    private Long transactionId;

    @Column
    private Long userId;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    @JsonIgnore
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
