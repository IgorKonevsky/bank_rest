package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO ответа, содержащий полную информацию о карте для администратора.
 * <p>
 * Эта запись предоставляет администратору полные данные о карте, включая ID и баланс.
 *
 * @param id          Уникальный идентификатор (UUID) карты.
 * @param ownerName   Имя владельца карты.
 * @param cardNumber  Полный номер карты.
 * @param expiryDate  Срок действия карты.
 * @param cardStatus  Текущий статус карты (например, ACTIVE, BLOCKED).
 * @param balance     Текущий баланс карты.
 */
@Schema(description = "DTO ответа с данными карты для администратора")
public record CardAdminResponseDto(
        UUID id,
        String ownerName,
        String cardNumber,
        LocalDate expiryDate,
        CardStatus cardStatus,
        BigDecimal balance
) {}