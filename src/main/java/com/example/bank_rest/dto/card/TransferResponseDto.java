package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO ответа после перевода")
public record TransferResponseDto(
        String sourceCardLastFourNumbers,
        BigDecimal sourceCardNewBalance,
        String destinationCard,
        BigDecimal destinationCardLastFourNumbers,
        BigDecimal transferredAmount
) {}
