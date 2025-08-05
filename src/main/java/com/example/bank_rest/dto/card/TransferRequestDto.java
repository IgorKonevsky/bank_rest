package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO запроса перевода между картами")
public record TransferRequestDto(
        String sourceCardLastFourNumbers,
        String destinationCardLastFourNumbers,
        BigDecimal amount
) {}
