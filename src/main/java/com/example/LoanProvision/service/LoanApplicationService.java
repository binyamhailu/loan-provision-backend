package com.example.LoanProvision.service;


import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.entity.Borrower;
import com.example.LoanProvision.entity.Loan;
import com.example.LoanProvision.entity.LoanApplication;
import com.example.LoanProvision.exception.ResourceNotFoundException;
import com.example.LoanProvision.exception.ValidationException;
import com.example.LoanProvision.repository.BorrowerRepository;
import com.example.LoanProvision.repository.LoanApplicationRepository;
import com.example.LoanProvision.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanApplicationDTO applyForLoan(LoanApplicationDTO loanApplicationDTO) {
        Borrower borrower = borrowerRepository.findById(loanApplicationDTO.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        if (borrower.getCreditScore() < 600) {
            throw new ValidationException("Credit score is too low");
        }

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setBorrower(borrower);
        loanApplication.setLoanAmount(loanApplicationDTO.getLoanAmount());
        loanApplication.setTerm(loanApplicationDTO.getTerm());
        loanApplication.setPurpose(loanApplicationDTO.getPurpose());
        loanApplication.setStatus("PENDING");
        loanApplication.setCreatedAt(LocalDateTime.now());
        loanApplication.setUpdatedAt(LocalDateTime.now());

        loanApplication = loanApplicationRepository.save(loanApplication);
        return convertToLoanApplicationDTO(loanApplication);
    }

    public LoanDTO approveLoanApplication(Long loanApplicationId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found"));

        loanApplication.setStatus("APPROVED");
        loanApplication.setUpdatedAt(LocalDateTime.now());
        loanApplicationRepository.save(loanApplication);

        Loan loan = new Loan();
        loan.setBorrower(loanApplication.getBorrower());
        loan.setLoanAmount(loanApplication.getLoanAmount());
        loan.setTerm(loanApplication.getTerm());
        loan.setPurpose(loanApplication.getPurpose());
        loan.setStatus("APPROVED");
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());

        loan = loanRepository.save(loan);
        return convertToLoanDTO(loan);
    }

    public LoanApplicationDTO rejectLoanApplication(Long loanApplicationId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found"));

        loanApplication.setStatus("REJECTED");
        loanApplication.setUpdatedAt(LocalDateTime.now());

        loanApplication = loanApplicationRepository.save(loanApplication);
        return convertToLoanApplicationDTO(loanApplication);
    }

    private LoanApplicationDTO convertToLoanApplicationDTO(LoanApplication loanApplication) {
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setId(loanApplication.getId());
        loanApplicationDTO.setBorrowerId(loanApplication.getBorrower().getId());
        loanApplicationDTO.setLoanAmount(loanApplication.getLoanAmount());
        loanApplicationDTO.setTerm(loanApplication.getTerm());
        loanApplicationDTO.setPurpose(loanApplication.getPurpose());
        loanApplicationDTO.setStatus(loanApplication.getStatus());
        loanApplicationDTO.setCreatedAt(loanApplication.getCreatedAt());
        loanApplicationDTO.setUpdatedAt(loanApplication.getUpdatedAt());
        return loanApplicationDTO;
    }

    private LoanDTO convertToLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setBorrowerInfo(loan.getBorrower().getName());
        loanDTO.setLoanAmount(loan.getLoanAmount());
        loanDTO.setTerm(loan.getTerm());
        loanDTO.setPurpose(loan.getPurpose());
        loanDTO.setStatus(loan.getStatus());
        loanDTO.setCreatedAt(loan.getCreatedAt());
        loanDTO.setUpdatedAt(loan.getUpdatedAt());
        return loanDTO;
    }
}

