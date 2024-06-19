
```markdown
## Loan Provisioning System

## Overview

The Loan Provisioning System is a Spring Boot application designed to manage the loan provisioning process, including loan applications, approvals, disbursements, and repayments. The application uses MySQL as its database and is containerized using Docker.

## Features

- Loan Application Submission
- Loan Approval Process
- Loan Disbursement
- Loan Repayment
- Loan Status and History
- RESTful API Documentation with Swagger
- Health Monitoring with Actuator

## Prerequisites

- Java 17
- Maven 3.6+
- Docker and Docker Compose

## Running the Application

### Using Maven

1. Clone the repository:
   
   git clone https://github.com/your-repo/loan-provisioning-system.git
   cd loan-provisioning-system
   ```

2. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

3. **Access the application:**
    - Swagger UI: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
    - Actuator: [http://localhost:8080/actuator/](http://localhost:8080/actuator/)

### Using Docker Compose

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-repo/loan-provisioning-system.git
   cd loan-provisioning-system
   ```

2. **Build and run the containers:**
   ```sh
   docker-compose up --build
   ```

3. **Access the application:**
    - Swagger UI: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
    - Actuator: [http://localhost:8080/actuator/](http://localhost:8080/actuator/)

## API Documentation

The API documentation is available via Swagger. You can explore and test the API endpoints using the Swagger UI.

- Swagger URL: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

## Health Monitoring

The application uses Spring Boot Actuator to provide health monitoring endpoints.

- Actuator URL: [http://localhost:8080/actuator/](http://localhost:8080/actuator/)

## Database

The application uses MySQL as the database. When running with Docker Compose, the database is automatically set up and initialized with the required schema.

### Database Configuration

- Host: `localhost`
- Port: `3306`
- Database Name: `loan_provisioning_db`
- Username: `root`
- Password: `my-secret-pw`
```