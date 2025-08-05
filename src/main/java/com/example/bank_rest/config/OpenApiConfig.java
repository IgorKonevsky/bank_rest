package com.example.bank_rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.parameters.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Bank REST API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Authorization header using the Bearer scheme"
)
public class OpenApiConfig {


    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            String path = handlerMethod.getMethodAnnotation(RequestMapping.class) != null
                    ? Objects.requireNonNull(handlerMethod.getMethodAnnotation(RequestMapping.class)).value()[0]
                    : "";

            if (path.startsWith("/api/v1/auth") || path.startsWith("/api/v1/signup")  || path.startsWith("/v3/api-docs")
                    || path.startsWith("/swagger-ui") || path.startsWith("/swagger-ui.html") || path.startsWith("/webjars")) {
                return operation;
            }

            operation.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth"));
            return operation;
        };
    }
}
