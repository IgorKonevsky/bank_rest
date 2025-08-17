package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Интерфейс сервиса для операций регистрации.
 * Определяет метод для регистрации новых пользователей.
 */
public interface RegistrationService {

    /**
     * Регистрирует нового пользователя.
     *
     * @param registerRequestDto DTO, содержащий данные для регистрации.
     * @return DTO {@link RegisterResponseDto} с данными зарегистрированного пользователя.
     */
    RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto);
}