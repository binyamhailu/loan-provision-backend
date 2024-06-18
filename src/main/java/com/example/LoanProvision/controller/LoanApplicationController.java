package com.example.LoanProvision.controller;


import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/loan-applications")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<LoanApplicationDTO> applyForLoan(@Valid @RequestBody LoanApplicationDTO loanApplicationDTO) {
        LoanApplicationDTO loanApplicationDTOResponse = loanApplicationService.applyForLoan(loanApplicationDTO);
        return ResponseEntity.status(201).body(loanApplicationDTOResponse);
    }

    @PutMapping("/{loanApplicationId}/approve")
    public ResponseEntity<LoanDTO> approveLoanApplication(@PathVariable Long loanApplicationId) {
        LoanDTO loanDTO = loanApplicationService.approveLoanApplication(loanApplicationId);
        return ResponseEntity.ok(loanDTO);
    }

    @PutMapping("/{loanApplicationId}/reject")
    public ResponseEntity<LoanApplicationDTO> rejectLoanApplication(@PathVariable Long loanApplicationId) {
        LoanApplicationDTO loanApplicationDTO = loanApplicationService.rejectLoanApplication(loanApplicationId);
        return ResponseEntity.ok(loanApplicationDTO);
    }
}

