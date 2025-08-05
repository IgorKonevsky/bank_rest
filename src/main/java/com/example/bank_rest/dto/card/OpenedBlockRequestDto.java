package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.BlockRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO ответа с открытым запросом блокировки карты")
public record OpenedBlockRequestDto(
        String cardNumber,
        BlockRequestStatus blockRequestStatus,
        LocalDateTime blockRequestDateTime
) {}
