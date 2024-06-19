package com.example.LoanProvision.controller;

import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loan-applications")
public class LoanApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationController.class);

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<LoanApplicationDTO> applyForLoan(@Valid @RequestBody LoanApplicationDTO loanApplicationDTO) {
        logger.info("API call to apply for a loan for borrower ID: {}", loanApplicationDTO.getBorrowerId());
        LoanApplicationDTO loanApplicationDTOResponse = loanApplicationService.applyForLoan(loanApplicationDTO);
        return ResponseEntity.status(201).body(loanApplicationDTOResponse);
    }

    @PutMapping("/{loanApplicationId}/approve")
    public ResponseEntity<LoanDTO> approveLoanApplication(@PathVariable Long loanApplicationId) {
        logger.info("API call to approve loan application ID: {}", loanApplicationId);
        LoanDTO loanDTO = loanApplicationService.approveLoanApplication(loanApplicationId);
        return ResponseEntity.ok(loanDTO);
    }

    @PutMapping("/{loanApplicationId}/reject")
    public ResponseEntity<LoanApplicationDTO> rejectLoanApplication(@PathVariable Long loanApplicationId) {
        logger.info("API call to reject loan application ID: {}", loanApplicationId);
        LoanApplicationDTO loanApplicationDTO = loanApplicationService.rejectLoanApplication(loanApplicationId);
        return ResponseEntity.ok(loanApplicationDTO);
    }
}
