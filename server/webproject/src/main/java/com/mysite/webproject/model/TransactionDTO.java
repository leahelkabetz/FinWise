package com.mysite.webproject.model;
import lombok.Data;

import java.time.LocalDate;
@Data
public class TransactionDTO {
    private Long transactionId;
    private LocalDate date;
    private String description;
    private Double amount;
    private String categoryName;
    private String categoryTypeLabel;
    private boolean isFixed;
    private Double balance;
}
