package com.metabotrackapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "MetaboTrack API",
                version = "1.0",
                description = "MetaboTrack API Documentation"
        ),
        security = @SecurityRequirement(name = "Authorization"),
        tags = {
        @Tag(name = "1. User Authentication", description = "Endpoints for managing user accounts, including registration, login, and logout."),
        @Tag(name = "2. Record Management", description = "Standardized endpoints for managing daily metabolic and activity data snapshots."),
        @Tag(name = "3. Advanced Analytics (Insights)", description = "High-level data aggregation and predictive analytics endpoints based on population snapshots.")
}
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "Please enter your JWT token here. Swagger will automatically prepend 'Bearer ' for you."
)
public class OpenApiConfig {
}
