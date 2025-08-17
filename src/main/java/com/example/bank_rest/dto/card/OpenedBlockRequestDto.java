package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.BlockRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO ответа, содержащий информацию об открытом запросе на блокировку карты.
 * <p>
 * Эта запись используется для передачи данных клиенту после создания запроса на блокировку.
 *
 * @param cardNumber             Номер карты, для которой был создан запрос.
 * @param blockRequestStatus     Текущий статус запроса на блокировку (например, PENDING).
 * @param blockRequestDateTime   Дата и время создания запроса.
 */
@Schema(description = "DTO ответа с открытым запросом блокировки карты")
public record OpenedBlockRequestDto(
        String cardNumber,
        BlockRequestStatus blockRequestStatus,
        LocalDateTime blockRequestDateTime
) {}