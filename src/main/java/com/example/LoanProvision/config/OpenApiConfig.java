package com.example.LoanProvision.config;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Binyam",
                        email = "binwelbeck@gmail.com",
                        url = "binwelbeck@gmail.com"
                ),
                description = "OpenApi documentation for Loan Provision",
                title = "OpenApi specification ",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://www.linkedin.com/in/binkid1/"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080"
                )
        }

)

public class OpenApiConfig {
}

