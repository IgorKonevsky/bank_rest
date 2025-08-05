package com.example.bank_rest.controller;

import com.example.bank_rest.dto.auth.AuthRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Tag(name = "Authentication", description = "Аутентификация пользователей")
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @Operation(summary = "Авторизация пользователя", description = "Получение JWT токена")
    @PostMapping
    public Map<String, Object> authHandler(
            @Parameter(description = "Данные для авторизации", required = true)
            @RequestBody AuthRequestDto authRequestDto);
}
