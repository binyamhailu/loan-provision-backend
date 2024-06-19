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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


@Service
public class LoanApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationService.class);

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanApplicationDTO applyForLoan(LoanApplicationDTO loanApplicationDTO) {
        logger.info("Applying for loan for borrower ID: {}", loanApplicationDTO.getBorrowerId());
        Borrower borrower = borrowerRepository.findById(loanApplicationDTO.getBorrowerId())
                .orElseThrow(() -> {
                    logger.error("Borrower not found with ID: {}", loanApplicationDTO.getBorrowerId());
                    return new ResourceNotFoundException("Borrower not found");
                });

        if (borrower.getCreditScore() < 600) {
            logger.error("Credit score too low for borrower ID: {}", loanApplicationDTO.getBorrowerId());
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
        logger.info("Loan application submitted successfully for borrower ID: {}", loanApplicationDTO.getBorrowerId());
        return convertToLoanApplicationDTO(loanApplication);
    }

    public LoanDTO approveLoanApplication(Long loanApplicationId) {
        logger.info("Approving loan application ID: {}", loanApplicationId);
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> {
                    logger.error("Loan application not found with ID: {}", loanApplicationId);
                    return new ResourceNotFoundException("Loan application not found");
                });

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
        logger.info("Loan application approved successfully with ID: {}", loanApplicationId);
        return convertToLoanDTO(loan);
    }

    public LoanApplicationDTO rejectLoanApplication(Long loanApplicationId) {
        logger.info("Rejecting loan application ID: {}", loanApplicationId);
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> {
                    logger.error("Loan application not found with ID: {}", loanApplicationId);
                    return new ResourceNotFoundException("Loan application not found");
                });

        loanApplication.setStatus("REJECTED");
        loanApplication.setUpdatedAt(LocalDateTime.now());

        loanApplication = loanApplicationRepository.save(loanApplication);
        logger.info("Loan application rejected successfully with ID: {}", loanApplicationId);
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
