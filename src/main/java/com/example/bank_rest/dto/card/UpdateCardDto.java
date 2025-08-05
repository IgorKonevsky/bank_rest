package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO для запроса обновления карты")
public record UpdateCardDto(
        UUID id,
        CardStatus cardStatus,
        BigDecimal balance
) {}
