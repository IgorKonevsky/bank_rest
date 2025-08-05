package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.BlockRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO для запросов блокировки карты администратора")
public record AdminCardBlockRequestDto(
        UUID id,
        BlockRequestStatus status,
        LocalDateTime requestedAt,
        UUID cardId,
        String cardNumber
) {}
