package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.BlockRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO для административных запросов на блокировку карты.
 * <p>
 * Эта запись используется для предоставления администратору детальной информации о запросах на блокировку.
 *
 * @param id            Уникальный идентификатор запроса на блокировку.
 * @param status        Текущий статус запроса.
 * @param requestedAt   Дата и время создания запроса.
 * @param cardId        Уникальный идентификатор (UUID) карты, для которой был сделан запрос.
 * @param cardNumber    Номер карты, для которой был создан запрос.
 */
@Schema(description = "DTO для запросов блокировки карты администратора")
public record AdminCardBlockRequestDto(
        UUID id,
        BlockRequestStatus status,
        LocalDateTime requestedAt,
        UUID cardId,
        String cardNumber
) {}