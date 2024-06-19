package com.example.LoanProvision;



import com.example.LoanProvision.dto.LoanApplicationDTO;
import com.example.LoanProvision.dto.LoanDTO;
import com.example.LoanProvision.dto.RepaymentDTO;
import com.example.LoanProvision.entity.Borrower;
import com.example.LoanProvision.entity.Loan;
import com.example.LoanProvision.repository.BorrowerRepository;
import com.example.LoanProvision.repository.LoanRepository;
import com.example.LoanProvision.repository.RepaymentRepository;
import com.example.LoanProvision.service.LoanApplicationService;
import com.example.LoanProvision.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {LoadProvisionApplication.class, TestContainersConfig.class})
public class LoanProvisioningSystemApplicationTests {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Test
    void contextLoads() {
    }

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    private Borrower borrower;
    private Loan loan;

    @BeforeEach
    public void setup() {
        borrower = new Borrower();
        borrower.setName("Binyam Hailu");
        borrower.setEmail("binyamhailu@example.com");
        borrower.setCreditScore(720);
        borrower.setIncome(BigDecimal.valueOf(75000));
        borrower.setDebtToIncomeRatio(BigDecimal.valueOf(0.30));
        borrower.setBalance(BigDecimal.valueOf(0));
        borrower = borrowerRepository.save(borrower);

        loan = new Loan();
        loan.setBorrower(borrower);
        loan.setLoanAmount(BigDecimal.valueOf(10000));
        loan.setTerm(36);
        loan.setPurpose("Car Purchase");
        loan.setStatus("APPROVED");
        loan.setBalance(BigDecimal.valueOf(0));
        loan.setRate(BigDecimal.valueOf(0.05));
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
        loan = loanRepository.save(loan);
    }

    @Test
    public void testDisburseLoan() {
        LoanDTO loanDTO = loanService.disburseLoan(loan.getId());
        assertNotNull(loanDTO);
        assertEquals("DISBURSED", loanDTO.getStatus());
        assertEquals(BigDecimal.valueOf(10000).setScale(2), loanDTO.getBalance().setScale(2));
        Borrower updatedBorrower = borrowerRepository.findById(borrower.getId()).orElse(null);
        assertNotNull(updatedBorrower);
        assertEquals(BigDecimal.valueOf(10000).setScale(2), updatedBorrower.getBalance().setScale(2));
    }

    @Test
    public void testRepayLoan() {
        loanService.disburseLoan(loan.getId());
        RepaymentDTO repaymentDTO = new RepaymentDTO();
        repaymentDTO.setAmount(BigDecimal.valueOf(500));

        LoanDTO loanDTO = loanService.repayLoan(loan.getId(), repaymentDTO);
        assertNotNull(loanDTO);
        assertNotEquals(BigDecimal.valueOf(9500).setScale(2), loanDTO.getBalance().setScale(2));
        Borrower updatedBorrower = borrowerRepository.findById(borrower.getId()).orElse(null);
        assertNotNull(updatedBorrower);
    }

    @Test
    public void testGetLoanDetails() {
        loanService.disburseLoan(loan.getId());
        LoanDTO loanDTO = loanService.getLoanDetails(loan.getId());
        assertNotNull(loanDTO);
        assertEquals(loan.getId(), loanDTO.getId());
    }


}

