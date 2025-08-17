package com.example.bank_rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Класс конфигурации для OpenAPI 3.0 и Swagger UI.
 * <p>
 * Этот класс настраивает автоматическую генерацию документации для REST API.
 * Он определяет общую информацию о приложении, схему безопасности для JWT-токенов
 * и кастомизирует операции, чтобы автоматически добавлять требование аутентификации
 * ко всем конечным точкам, кроме тех, которые предназначены для публичного доступа (аутентификация, регистрация).
 */
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

    /**
     * Создает и настраивает бин {@link OperationCustomizer}.
     * <p>
     * Этот бин используется для кастомизации операций API, сгенерированных Swagger'ом.
     * Он проверяет, соответствует ли путь конечной точки одному из публичных путей
     * (`/api/v1/auth`, `/api/v1/signup`, а также пути для самой документации).
     * <p>
     * Если путь не является публичным, к операции добавляется требование безопасности
     * "bearerAuth", что заставляет Swagger UI отображать замок рядом с конечной точкой,
     * указывая на необходимость аутентификации.
     *
     * @return Бин {@link OperationCustomizer} для применения кастомизации к операциям.
     */
    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            RequestMapping mapping = handlerMethod.getMethodAnnotation(RequestMapping.class);

            String path = "";
            if (mapping != null && mapping.value().length > 0) {
                path = mapping.value()[0];
            }


            if (path.startsWith("/api/v1/auth") ||
                    path.startsWith("/api/v1/signup") ||
                    path.startsWith("/v3/api-docs") ||
                    path.startsWith("/swagger-ui") ||
                    path.startsWith("/swagger-ui.html") ||
                    path.startsWith("/webjars")) {
                return operation;
            }


            operation.addSecurityItem(
                    new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth")
            );
            return operation;
        };
    }
}