package com.example.bank_rest.service;

/**
 * Интерфейс сервиса для операций выхода из системы.
 * Определяет метод для аннулирования токена.
 */
public interface LogoutService {

    /**
     * Аннулирует JWT-токен, предоставленный в заголовке `Authorization`.
     *
     * @param authHeader Заголовок "Authorization" с JWT-токеном.
     * @return String
     */
    String invalidateToken(String authHeader);
}