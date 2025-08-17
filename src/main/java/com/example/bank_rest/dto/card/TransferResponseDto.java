package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO ответа, содержащий информацию о результате операции перевода.
 * <p>
 * Эта запись используется для предоставления клиенту данных о выполненном переводе.
 *
 * @param sourceCardLastFourNumbers   Последние четыре цифры номера карты-источника.
 * @param sourceCardNewBalance        Новый баланс карты-источника после перевода.
 * @param destinationCard             Номер карты-назначения.
 * @param destinationCardLastFourNumbers Последние четыре цифры номера карты-назначения.
 * @param transferredAmount           Переведенная сумма.
 */
@Schema(description = "DTO ответа после перевода")
public record TransferResponseDto(
        String sourceCardLastFourNumbers,
        BigDecimal sourceCardNewBalance,
        String destinationCard,
        BigDecimal destinationCardLastFourNumbers,
        BigDecimal transferredAmount
) {}