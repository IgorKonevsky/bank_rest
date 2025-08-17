package com.example.bank_rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Интерфейс контроллера для выхода пользователей из системы.
 */
@Tag(name = "Logout", description = "Выход пользователя")
@RequestMapping("/api/v1/logout")
public interface LogoutController {

    /**
     * Обрабатывает выход пользователя из системы, аннулируя его JWT-токен.
     * <p>
     *
     * @param authHeader Заголовок HTTP-запроса "Authorization", содержащий JWT-токен.
     * @param response   Объект {@link HttpServletResponse} для управления ответом.
     * @return {@link ResponseEntity} без содержимого, указывающий на успешный выход.
     */
    @Operation(summary = "Выход из системы")
    @PostMapping
    public ResponseEntity<?> logout(
            @Parameter(description = "Заголовок Authorization", required = true, example = "Bearer <token>") @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response);
}