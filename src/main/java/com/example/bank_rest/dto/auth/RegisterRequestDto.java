package com.example.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO регистрации пользователя")
public record RegisterRequestDto(
        @Schema(description = "Имя пользователя", example = "Иван")
        String firstName,

        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String lastName,

        @Schema(description = "Отчество пользователя", example = "Иванович")
        String patronymic,

        @Schema(description = "Имя пользователя для входа", example = "vanek")
        String username,

        @Schema(description = "Пароль пользователя", example = "strongPassword123")
        String password
) {}
