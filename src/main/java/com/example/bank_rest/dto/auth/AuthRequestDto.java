package com.example.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO для запроса на аутентификацию.
 * <p>
 * Эта запись используется для передачи данных пользователя (логина и пароля)
 * при попытке входа в систему.
 *
 * @param username Имя пользователя.
 * @param password Пароль пользователя.
 */
@Schema(description = "DTO для запроса аутентификации")
public record AuthRequestDto(
        @Schema(description = "Имя пользователя", example = "testUser")
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, message = "Username must be at least 3 characters long")
        String username,
        @Schema(description = "Пароль", example = "testPassword")
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}