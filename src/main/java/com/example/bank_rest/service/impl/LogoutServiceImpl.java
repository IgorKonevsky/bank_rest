package com.example.bank_rest.service.impl;

import com.example.bank_rest.entity.InvalidToken;
import com.example.bank_rest.repository.InvalidTokenRepository;
import com.example.bank_rest.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Реализация сервиса для выхода из системы.
 * Этот класс предоставляет бизнес-логику для аннулирования JWT-токенов.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final InvalidTokenRepository invalidTokenRepository;


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String invalidateToken(String authHeader) {
        String token = authHeader.substring(7);

        if (invalidTokenRepository.existsByToken(token)) {
            log.info("Token already invalidated: {}", token);
            return "Token already invalidated";
        }

        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setToken(token);
        invalidToken.setExpiryDate(new Date(System.currentTimeMillis()));
        invalidTokenRepository.save(invalidToken);

        return "Logout successful";
    }
}
