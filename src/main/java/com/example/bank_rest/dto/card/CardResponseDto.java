package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO ответа с данными карты для клиента")
public record CardResponseDto(
        String ownerName,
        String cardNumber,
        LocalDate expiryDate,
        CardStatus cardStatus,
        BigDecimal balance
) {}
