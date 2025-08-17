package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO запроса на перевод средств между картами.
 * <p>
 * Эта запись используется для передачи данных от клиента для выполнения перевода между его картами.
 *
 * @param sourceCardLastFourNumbers        Последние четыре цифры номера карты-источника.
 * @param destinationCardLastFourNumbers   Последние четыре цифры номера карты-назначения.
 * @param amount                           Сумма перевода.
 */
@Schema(description = "DTO запроса перевода между картами")
public record TransferRequestDto(
        String sourceCardLastFourNumbers,
        String destinationCardLastFourNumbers,
        BigDecimal amount
) {}