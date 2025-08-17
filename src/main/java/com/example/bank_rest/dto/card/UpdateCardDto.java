package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для запроса обновления данных карты.
 * <p>
 * Эта запись используется для передачи данных от администратора для обновления статуса или баланса карты.
 *
 * @param id          Уникальный идентификатор (UUID) карты.
 * @param cardStatus  Новый статус карты.
 * @param balance     Новый баланс карты.
 */
@Schema(description = "DTO для запроса обновления карты")
public record UpdateCardDto(
        UUID id,
        CardStatus cardStatus,
        BigDecimal balance
) {}