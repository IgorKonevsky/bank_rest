package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.AuthRequestDto;
import java.util.Map;

/**
 * Интерфейс сервиса для операций аутентификации.
 * Определяет метод для обработки запросов на аутентификацию.
 */
public interface AuthService {

    /**
     * Обрабатывает запрос на аутентификацию.
     *
     * @param authRequestDto DTO, содержащий данные для авторизации (логин и пароль).
     * @return {@link Map}, содержащий информацию об аутентификации, включая JWT-токен.
     */
    Map<String, Object> handleAuth(AuthRequestDto authRequestDto);
}