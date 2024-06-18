package com.example.LoanProvision.service;

import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.dto.RepaymentDTO;
import com.example.LoanProvision.entity.Borrower;
import com.example.LoanProvision.entity.Loan;
import com.example.LoanProvision.entity.LoanApplication;
import com.example.LoanProvision.entity.Repayment;
import com.example.LoanProvision.exception.ResourceNotFoundException;
import com.example.LoanProvision.exception.ValidationException;
import com.example.LoanProvision.repository.BorrowerRepository;
import com.example.LoanProvision.repository.LoanApplicationRepository;
import com.example.LoanProvision.repository.LoanRepository;
import com.example.LoanProvision.repository.RepaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    public LoanDTO disburseLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (!loan.getStatus().equals("APPROVED")) {
            throw new ValidationException("Loan is not approved for disbursement");
        }

        loan.setStatus("DISBURSED");
        loan.setUpdatedAt(LocalDateTime.now());

        loan = loanRepository.save(loan);
        return convertToLoanDTO(loan);
    }

    public LoanDTO repayLoan(Long loanId, RepaymentDTO repaymentDTO) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Repayment repayment = new Repayment();
        repayment.setLoan(loan);
        repayment.setAmount(repaymentDTO.getAmount());
        repayment.setDate(LocalDateTime.now());

        repaymentRepository.save(repayment);

        loan.setUpdatedAt(LocalDateTime.now());
        loanRepository.save(loan);

        return convertToLoanDTO(loan);
    }

    public LoanDTO getLoanDetails(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        return convertToLoanDTO(loan);
    }

    public List<RepaymentDTO> getRepaymentHistory(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        return loan.getRepayments().stream()
                .map(this::convertToRepaymentDTO)
                .collect(Collectors.toList());
    }

    private LoanDTO convertToLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setBorrowerInfo(loan.getBorrowerInfo());
        loanDTO.setLoanAmount(loan.getLoanAmount());
        loanDTO.setTerm(loan.getTerm());
        loanDTO.setPurpose(loan.getPurpose());
        loanDTO.setStatus(loan.getStatus());
        loanDTO.setCreatedAt(loan.getCreatedAt());
        loanDTO.setUpdatedAt(loan.getUpdatedAt());
        return loanDTO;
    }

    private RepaymentDTO convertToRepaymentDTO(Repayment repayment) {
        RepaymentDTO repaymentDTO = new RepaymentDTO();
        repaymentDTO.setId(repayment.getId());
        repaymentDTO.setLoanId(repayment.getLoan().getId());
        repaymentDTO.setAmount(repayment.getAmount());
        repaymentDTO.setDate(repayment.getDate());
        return repaymentDTO;
    }
}