package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.AuthController;
import com.example.bank_rest.dto.auth.AuthRequestDto;
import com.example.bank_rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * Реализация интерфейса {@link AuthController}.
 * Этот класс обрабатывает входящие HTTP-запросы для аутентификации пользователей.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    /**
     * Обрабатывает запросы на аутентификацию.
     * Логирует входящие данные и передает их в {@link AuthService} для обработки.
     *
     * @param authRequestDto DTO, содержащий данные для авторизации (логин и пароль).
     * @return {@link Map}, содержащий информацию об аутентификации.
     */
    @Override
    public Map<String, Object> authHandler(AuthRequestDto authRequestDto) {
        log.info("Class: AuthControllerImpl, method: authHandler, authRequestDto = {}", authRequestDto.toString());
        return authService.handleAuth(authRequestDto);

    }
}
