package com.example.bank_rest.controller;

import com.example.bank_rest.dto.auth.AuthRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Интерфейс контроллера для операций аутентификации.
 * Предоставляет конечную точку для входа пользователей в систему.
 */
@Tag(name = "Authentication", description = "Аутентификация пользователей")
@RequestMapping("/api/v1/auth")
public interface AuthController {
    /**
     * Обрабатывает запросы на аутентификацию и возвращает JWT-токен.
     * <p>
     *
     * @param authRequestDto DTO, содержащий данные для авторизации пользователя (логин и пароль).
     * @return {@link Map}, содержащий информацию об аутентификации, включая JWT-токен.
     */
    @Operation(summary = "Авторизация пользователя", description = "Получение JWT токена")
    @PostMapping
    public Map<String, Object> authHandler(
            @Parameter(description = "Данные для авторизации", required = true)
            @RequestBody AuthRequestDto authRequestDto);
}
