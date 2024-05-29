package com.example.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot test",
                version = "1.0.0",
                description = "Spring boot test for the data",
                contact = @Contact(name = "Gozbert Stanslaus", email = "gozbeths@gmail.com"),
                termsOfService = "T&C"
        ),
        servers = {
                @Server(url = "${springdoc.api-docs.server}", description = "Default Server URL")
        }
)
@SecurityScheme(name = "TEST", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
}
