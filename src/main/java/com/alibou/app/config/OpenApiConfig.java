package com.alibou.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring Security JWT Asymmetric Encryption demo",
                        email = "contact@libou.com",
                        url = "https://alibou.com"
                ),
                description = "OpenAp documentation for Spring Security project",
                title = "OpenApi Specification",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://alibou.com/license"
                )
                ,termsOfService = "https://alibou.com/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Env"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://prod.url"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
//gives us the definition of the property or configuration where we want to inject our security once it's injected. Clarify the prupose for this annotation
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
