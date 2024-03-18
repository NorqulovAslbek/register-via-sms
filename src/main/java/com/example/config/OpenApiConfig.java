package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Aslbek",
                        email = "aslbeknorqulov246@gmail.com",
                        url = "https://t.me/asl_bec"
                ),
                description = "a list of the project's public APIs",
                title = "Open Api specification - Test",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local environment",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod environment",
                        url = "http://localhost:8081"
                )
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
