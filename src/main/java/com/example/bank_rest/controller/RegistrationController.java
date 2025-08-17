package com.example.bank_rest.controller;

import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Интерфейс контроллера для регистрации новых пользователей.
 * Предоставляет конечную точку для создания новых учетных записей.
 */
@Tag(name = "Registration", description = "Регистрация новых пользователей")
@RequestMapping("/api/v1/signup")
public interface RegistrationController {

    /**
     * Регистрирует нового пользователя на основе предоставленных данных.
     * <p>
     *
     * @param registerRequestDto DTO, содержащий данные для регистрации пользователя (например, имя пользователя, пароль).
     * @return {@link ResponseEntity} с DTO, содержащим данные зарегистрированного пользователя.
     */
    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя")
    @PostMapping
    public ResponseEntity<RegisterResponseDto> registerUser(
            @Parameter(description = "Данные для регистрации", required = true)
            @Valid @RequestBody RegisterRequestDto registerRequestDto);
}