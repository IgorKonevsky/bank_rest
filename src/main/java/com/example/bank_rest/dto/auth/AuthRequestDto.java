package com.example.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO запроса аутентификации")
public record AuthRequestDto(
        @Schema(description = "Имя пользователя", example = "john.doe")
        String username,

        @Schema(description = "Пароль пользователя", example = "strongPassword123")
        String password
) {}
