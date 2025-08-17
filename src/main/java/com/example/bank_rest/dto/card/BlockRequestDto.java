package com.example.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO запроса на блокировку карты.
 * <p>
 * Эта запись используется для передачи данных от клиента для создания запроса на блокировку карты.
 *
 * @param lastFourNumbers Последние четыре цифры номера карты, которую нужно заблокировать.
 */
@Schema(description = "DTO запроса блокировки карты")
public record BlockRequestDto(
        String lastFourNumbers
) {}