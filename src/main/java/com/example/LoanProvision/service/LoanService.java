package com.example.LoanProvision.service;

import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.dto.RepaymentDTO;
import com.example.LoanProvision.entity.Borrower;
import com.example.LoanProvision.entity.Loan;
import com.example.LoanProvision.entity.Repayment;
import com.example.LoanProvision.exception.ResourceNotFoundException;
import com.example.LoanProvision.exception.ValidationException;
import com.example.LoanProvision.repository.BorrowerRepository;
import com.example.LoanProvision.repository.LoanRepository;
import com.example.LoanProvision.repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    public LoanDTO disburseLoan(Long loanId) {
        logger.info("Disbursing loan with ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    logger.error("Loan not found with ID: {}", loanId);
                    return new ResourceNotFoundException("Loan not found");
                });

        if (!loan.getStatus().equals("APPROVED")) {
            logger.error("Loan not approved for disbursement with ID: {}", loanId);
            throw new ValidationException("Loan is not approved for disbursement");
        }

        loan.setStatus("DISBURSED");
        loan.setBalance(loan.getLoanAmount());
        loan.setUpdatedAt(LocalDateTime.now());

        Borrower borrower = loan.getBorrower();
        borrower.setBalance(borrower.getBalance().add(loan.getLoanAmount()));
        borrowerRepository.save(borrower);

        loan = loanRepository.save(loan);
        logger.info("Loan disbursed successfully with ID: {}", loanId);
        return convertToLoanDTO(loan);
    }

    public LoanDTO repayLoan(Long loanId, RepaymentDTO repaymentDTO) {
        logger.info("Repaying loan with ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    logger.error("Loan not found with ID: {}", loanId);
                    return new ResourceNotFoundException("Loan not found");
                });

        BigDecimal interest = loan.getBalance().multiply(loan.getRate()).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        BigDecimal totalRepayment = repaymentDTO.getAmount().subtract(interest).setScale(2, RoundingMode.HALF_UP);

        if (loan.getBalance().compareTo(totalRepayment) < 0) {
            logger.error("Repayment amount exceeds the remaining balance for loan ID: {}", loanId);
            throw new ValidationException("Repayment amount exceeds the remaining balance");
        }

        Repayment repayment = new Repayment();
        repayment.setLoan(loan);
        repayment.setAmount(repaymentDTO.getAmount());
        repayment.setDate(LocalDateTime.now());

        repaymentRepository.save(repayment);

        loan.setBalance(loan.getBalance().subtract(totalRepayment));
        loan.setUpdatedAt(LocalDateTime.now());
        loanRepository.save(loan);

        Borrower borrower = loan.getBorrower();
        borrower.setBalance(borrower.getBalance().subtract(repaymentDTO.getAmount()));
        borrowerRepository.save(borrower);

        logger.info("Loan repaid successfully with ID: {}", loanId);
        return convertToLoanDTO(loan);
    }

    public LoanDTO getLoanDetails(Long loanId) {
        logger.info("Fetching loan details for ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    logger.error("Loan not found with ID: {}", loanId);
                    return new ResourceNotFoundException("Loan not found");
                });

        return convertToLoanDTO(loan);
    }

    public List<RepaymentDTO> getRepaymentHistory(Long loanId) {
        logger.info("Fetching repayment history for loan ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    logger.error("Loan not found with ID: {}", loanId);
                    return new ResourceNotFoundException("Loan not found");
                });

        List<Repayment> repayments = loan.getRepayments();
        return repayments.stream()
                .map(this::convertToRepaymentDTO)
                .collect(Collectors.toList());
    }

    private LoanDTO convertToLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setBorrowerInfo(loan.getBorrower().getName() + ", " + loan.getBorrower().getEmail());
        loanDTO.setLoanAmount(loan.getLoanAmount());
        loanDTO.setTerm(loan.getTerm());
        loanDTO.setPurpose(loan.getPurpose());
        loanDTO.setStatus(loan.getStatus());
        loanDTO.setBalance(loan.getBalance());
        loanDTO.setCreatedAt(loan.getCreatedAt());
        loanDTO.setUpdatedAt(loan.getUpdatedAt());
        loanDTO.setRate(loan.getRate());
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
