package com.example.LoanProvision.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "borrowers")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @Column(name = "debt_to_income_ratio", nullable = false)
    private BigDecimal debtToIncomeRatio;

}
