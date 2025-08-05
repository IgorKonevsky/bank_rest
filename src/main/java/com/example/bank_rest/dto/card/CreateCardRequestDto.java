package com.example.bank_rest.dto.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO для запроса создания карты")
public record CreateCardRequestDto(
        @Schema(description = "UUID владельца карты", example = "7a189ebf-bbb7-4cf4-9c16-bea8ffba273c")
        UUID ownerId,

        @Schema(description = "Дата окончания действия карты", example = "31.12.2026")
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate expiryDate
) {}
