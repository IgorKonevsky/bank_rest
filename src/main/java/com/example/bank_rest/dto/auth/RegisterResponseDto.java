package com.example.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для ответа на успешную регистрацию.
 */
@Schema(description = "DTO ответа после успешной регистрации")
public record RegisterResponseDto(
        String firstName,
        String lastName,
        String username
) {
}