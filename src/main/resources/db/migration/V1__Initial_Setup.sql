-- Create Borrower Table
CREATE TABLE borrowers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    credit_score INT NOT NULL,
    income DECIMAL(15, 2) NOT NULL,
    debt_to_income_ratio DECIMAL(5, 2) NOT NULL
);

-- Create Loan Table
CREATE TABLE loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrower_info TEXT NOT NULL,
    loan_amount DECIMAL(15, 2) NOT NULL,
    term INT NOT NULL,
    purpose TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Repayment Table
CREATE TABLE repayments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loans(id)
);

-- Create LoanApplication Table
CREATE TABLE loan_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrower_id BIGINT NOT NULL,
    loan_amount DECIMAL(15, 2) NOT NULL,
    term INT NOT NULL,
    purpose TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (borrower_id) REFERENCES borrowers(id)
);
