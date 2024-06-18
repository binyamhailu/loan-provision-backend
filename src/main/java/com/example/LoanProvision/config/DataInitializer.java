package com.example.LoanProvision.config;


import com.example.LoanProvision.entity.Borrower;
import com.example.LoanProvision.entity.LoanApplication;
import com.example.LoanProvision.repository.BorrowerRepository;
import com.example.LoanProvision.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Override
    public void run(String... args) throws Exception {
        Borrower borrower1 = new Borrower();
        borrower1.setName("John Doe");
        borrower1.setEmail("john.doe@example.com");
        borrower1.setCreditScore(720);
        borrower1.setIncome(BigDecimal.valueOf(75000));
        borrower1.setDebtToIncomeRatio(BigDecimal.valueOf(0.30));
        borrowerRepository.save(borrower1);

        Borrower borrower2 = new Borrower();
        borrower2.setName("Jane Smith");
        borrower2.setEmail("jane.smith@example.com");
        borrower2.setCreditScore(680);
        borrower2.setIncome(BigDecimal.valueOf(85000));
        borrower2.setDebtToIncomeRatio(BigDecimal.valueOf(0.25));
        borrowerRepository.save(borrower2);

        LoanApplication loanApplication1 = new LoanApplication();
        loanApplication1.setBorrower(borrower1);
        loanApplication1.setLoanAmount(BigDecimal.valueOf(10000));
        loanApplication1.setTerm(36);
        loanApplication1.setPurpose("Car Purchase");
        loanApplication1.setStatus("PENDING");
        loanApplication1.setCreatedAt(LocalDateTime.now());
        loanApplication1.setUpdatedAt(LocalDateTime.now());
        loanApplicationRepository.save(loanApplication1);

        LoanApplication loanApplication2 = new LoanApplication();
        loanApplication2.setBorrower(borrower2);
        loanApplication2.setLoanAmount(BigDecimal.valueOf(5000));
        loanApplication2.setTerm(24);
        loanApplication2.setPurpose("Home Improvement");
        loanApplication2.setStatus("PENDING");
        loanApplication2.setCreatedAt(LocalDateTime.now());
        loanApplication2.setUpdatedAt(LocalDateTime.now());
        loanApplicationRepository.save(loanApplication2);

    }
}

