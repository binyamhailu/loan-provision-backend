CREATE TABLE borrowers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           credit_score INT NOT NULL,
                           income DECIMAL(15, 2) NOT NULL,
                           debt_to_income_ratio DECIMAL(5, 2) NOT NULL,
                           balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00
);

CREATE TABLE loans (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       borrower_id BIGINT NOT NULL,
                       loan_amount DECIMAL(15, 2) NOT NULL,
                       term INT NOT NULL,
                       purpose TEXT NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       balance DECIMAL(15, 2) NOT NULL,
                       rate DECIMAL(5, 4) NOT NULL,  -- Assuming rate as a decimal with precision up to 4 decimal places
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (borrower_id) REFERENCES borrowers(id)
);

CREATE TABLE repayments (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            loan_id BIGINT NOT NULL,
                            amount DECIMAL(15, 2) NOT NULL,
                            date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (loan_id) REFERENCES loans(id)
);

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
