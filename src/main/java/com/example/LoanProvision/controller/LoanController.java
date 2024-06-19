package com.example.LoanProvision.controller;

import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.dto.RepaymentDTO;
import com.example.LoanProvision.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanService loanService;

    @PostMapping("/{loanId}/disburse")
    public ResponseEntity<LoanDTO> disburseLoan(@PathVariable Long loanId) {
        logger.info("API call to disburse loan with ID: {}", loanId);
        LoanDTO loanDTO = loanService.disburseLoan(loanId);
        return ResponseEntity.ok(loanDTO);
    }

    @PostMapping("/{loanId}/repay")
    public ResponseEntity<LoanDTO> repayLoan(@PathVariable Long loanId, @Valid @RequestBody RepaymentDTO repaymentDTO) {
        logger.info("API call to repay loan with ID: {}", loanId);
        LoanDTO loanDTO = loanService.repayLoan(loanId, repaymentDTO);
        return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoanDetails(@PathVariable Long loanId) {
        logger.info("API call to get loan details for ID: {}", loanId);
        LoanDTO loanDTO = loanService.getLoanDetails(loanId);
        return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/{loanId}/history")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentHistory(@PathVariable Long loanId) {
        logger.info("API call to get repayment history for loan ID: {}", loanId);
        List<RepaymentDTO> repaymentHistory = loanService.getRepaymentHistory(loanId);
        return ResponseEntity.ok(repaymentHistory);
    }
}
