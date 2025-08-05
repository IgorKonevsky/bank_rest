package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO ответа с данными карты для администратора")
public record CardAdminResponseDto(
        UUID id,
        String ownerName,
        String cardNumber,
        LocalDate expiryDate,
        CardStatus cardStatus,
        BigDecimal balance
) {}
