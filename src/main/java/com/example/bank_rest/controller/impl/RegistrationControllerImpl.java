package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.RegistrationController;
import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import com.example.bank_rest.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Реализация интерфейса {@link RegistrationController}.
 * Этот класс обрабатывает входящие HTTP-запросы для регистрации.
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class RegistrationControllerImpl implements RegistrationController {

    private final RegistrationService registrationService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Class: RegistrationControllerImpl, method: registerUser, registerRequestDto: {}", registerRequestDto.toString());
        return ResponseEntity.ok(registrationService.registerUser(registerRequestDto));
    }
}