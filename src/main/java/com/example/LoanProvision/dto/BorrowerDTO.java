package com.example.LoanProvision.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BorrowerDTO {
    private Long id;
    private String name;
    private String email;
    private Integer creditScore;
    private BigDecimal income;
    private BigDecimal debtToIncomeRatio;
}

