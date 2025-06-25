package com.mysite.webproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue
    private Long categoryId;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;
}
