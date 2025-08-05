package com.example.bank_rest.controller;

import com.example.bank_rest.dto.card.*;
import com.example.bank_rest.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer", description = "Операции клиента")
@RequestMapping("/api/v1/customer")
@SecurityRequirement(name = "bearerAuth")
public interface CustomerController {



    @Operation(summary = "Получить карты клиента")
    @GetMapping("/cards")
    public Page<CardResponseDto> getCardsByCustomerId(
            @Parameter(description = "Текущий пользователь", hidden = true)
            @AuthenticationPrincipal User user,

            @Parameter(description = "Параметры пагинации и сортировки")
            @PageableDefault(size = 5, sort = "expiryDate") Pageable pageable,

            @Parameter(description = "Фильтр по последним 4 цифрам карты")
            @RequestParam(required = false, defaultValue = "") String cardNumber);

    @Operation(summary = "Перевод между своими картами")
    @PostMapping("/transfer")
    public TransferResponseDto transfer(
            @Parameter(description = "Текущий пользователь", hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "Данные для перевода", required = true) @RequestBody TransferRequestDto transferRequestDto);

    @Operation(summary = "Запрос блокировки карты")
    @PostMapping("/block")
    public OpenedBlockRequestDto requestBlock(
            @Parameter(description = "Текущий пользователь", hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "Данные запроса блокировки карты", required = true) @RequestBody BlockRequestDto blockRequestDto);
}
