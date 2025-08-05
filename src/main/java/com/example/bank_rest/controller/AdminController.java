package com.example.bank_rest.controller;

import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Admin", description = "Операции администратора")
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
public interface AdminController {

    @Operation(summary = "Получить список администраторов")
    @GetMapping("/list")
    public List<User> getAdminList();

    @Operation(summary = "Получить все карты")
    @GetMapping("/cards")
    public List<CardAdminResponseDto> getAllCards();

    @Operation(summary = "Получить карту по ID")
    @GetMapping("/cards/{id}")
    public CardAdminResponseDto getCardById(
            @Parameter(description = "UUID карты", required = true) @PathVariable UUID id);

    @Operation(summary = "Создать новую карту")
    @PostMapping("/cards")
    public CardAdminResponseDto createCard(
            @Parameter(description = "Данные для создания карты", required = true) @RequestBody CreateCardRequestDto createCardRequestDto);

    @Operation(summary = "Обновить карту")
    @PutMapping("/cards")
    public CardAdminResponseDto updateCard(
            @Parameter(description = "Данные для обновления карты", required = true) @RequestBody UpdateCardDto updateCardDto);

    @Operation(summary = "Удалить карту")
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<?> deleteCard(
            @Parameter(description = "UUID карты", required = true) @PathVariable UUID id);

    @Operation(summary = "Получить список запросов на блокировку карты")
    @GetMapping("/cards/block-requests")
    public List<AdminCardBlockRequestDto> pendingBlockRequests();
}
