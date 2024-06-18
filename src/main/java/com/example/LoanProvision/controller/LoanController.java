package com.example.LoanProvision.controller;


import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.dto.RepaymentDTO;
import com.example.LoanProvision.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/{loanId}/disburse")
    public ResponseEntity<LoanDTO> disburseLoan(@PathVariable Long loanId) {
        LoanDTO loanDTO = loanService.disburseLoan(loanId);
        return ResponseEntity.ok(loanDTO);
    }

    @PostMapping("/{loanId}/repay")
    public ResponseEntity<LoanDTO> repayLoan(@PathVariable Long loanId, @Valid @RequestBody RepaymentDTO repaymentDTO) {
        LoanDTO loanDTO = loanService.repayLoan(loanId, repaymentDTO);
        return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoanDetails(@PathVariable Long loanId) {
        LoanDTO loanDTO = loanService.getLoanDetails(loanId);
        return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/{loanId}/history")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentHistory(@PathVariable Long loanId) {
        List<RepaymentDTO> repaymentHistory = loanService.getRepaymentHistory(loanId);
        return ResponseEntity.ok(repaymentHistory);
    }
}
