package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO ответа, содержащий данные карты для клиента.
 * <p>
 * Эта запись предоставляет клиенту основную информацию о его картах.
 *
 * @param ownerName   Имя владельца карты.
 * @param cardNumber  Номер карты (частично скрытый или полный, в зависимости от логики).
 * @param expiryDate  Срок действия карты.
 * @param cardStatus  Текущий статус карты (например, ACTIVE, BLOCKED).
 * @param balance     Текущий баланс карты.
 */
@Schema(description = "DTO ответа с данными карты для клиента")
public record CardResponseDto(
        String ownerName,
        String cardNumber,
        LocalDate expiryDate,
        CardStatus cardStatus,
        BigDecimal balance
) {}