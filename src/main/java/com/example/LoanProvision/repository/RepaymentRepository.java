package com.example.LoanProvision.repository;

import com.example.LoanProvision.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
}
