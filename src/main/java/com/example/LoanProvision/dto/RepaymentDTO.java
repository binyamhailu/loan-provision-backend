package com.example.LoanProvision.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class RepaymentDTO {
    private Long id;
    private Long loanId;
    private BigDecimal amount;
    private LocalDateTime date;

}
