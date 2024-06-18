package com.example.LoanProvision.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class LoanApplicationDTO {
    private Long id;
    private Long borrowerId;
    private BigDecimal loanAmount;
    private Integer term;
    private String purpose;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
