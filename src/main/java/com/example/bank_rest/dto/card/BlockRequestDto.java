package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO запроса блокировки карты")
public record BlockRequestDto(
        String lastFourNumbers
) {}
