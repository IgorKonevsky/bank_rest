package com.example.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO ответа после регистрации")
public record RegisterResponseDto(
        String firstName,
        String lastName,
        String username
) {}
