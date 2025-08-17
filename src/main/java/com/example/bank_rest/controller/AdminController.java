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

/**
 * Интерфейс контроллера для операций, доступных администратору.
 * <p>
 * Требует аутентификации с помощью JWT-токена.
 */
@Tag(name = "Admin", description = "Операции администратора")
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
public interface AdminController {

    /**
     * Получает список всех администраторов в системе.
     *
     * @return Список объектов {@link User}, представляющих администраторов.
     */
    @Operation(summary = "Получить список администраторов")
    @GetMapping("/list")
    public List<User> getAdminList();

    /**
     * Получает список всех карт в системе.
     *
     * @return Список DTO-объектов {@link CardAdminResponseDto} для всех карт.
     */
    @Operation(summary = "Получить все карты")
    @GetMapping("/cards")
    public List<CardAdminResponseDto> getAllCards();

    /**
     * Получает карту по ее уникальному идентификатору (UUID).
     *
     * @param id Уникальный идентификатор (UUID) карты.
     * @return DTO-объект {@link CardAdminResponseDto} для найденной карты.
     */
    @Operation(summary = "Получить карту по ID")
    @GetMapping("/cards/{id}")
    public CardAdminResponseDto getCardById(
            @Parameter(description = "UUID карты", required = true) @PathVariable UUID id);

    /**
     * Создает новую карту в системе.
     *
     * @param createCardRequestDto DTO, содержащий данные для создания новой карты.
     * @return DTO-объект {@link CardAdminResponseDto} для созданной карты.
     */
    @Operation(summary = "Создать новую карту")
    @PostMapping("/cards")
    public CardAdminResponseDto createCard(
            @Parameter(description = "Данные для создания карты", required = true) @RequestBody CreateCardRequestDto createCardRequestDto);

    /**
     * Обновляет существующую карту.
     *
     * @param updateCardDto DTO, содержащий данные для обновления карты, включая ее идентификатор.
     * @return DTO-объект {@link CardAdminResponseDto} для обновленной карты.
     */
    @Operation(summary = "Обновить карту")
    @PutMapping("/cards")
    public CardAdminResponseDto updateCard(
            @Parameter(description = "Данные для обновления карты", required = true) @RequestBody UpdateCardDto updateCardDto);

    /**
     * Удаляет карту по ее уникальному идентификатору.
     *
     * @param id Уникальный идентификатор (UUID) карты, которую нужно удалить.
     * @return {@link ResponseEntity} без содержимого, указывающий на успешное удаление.
     */
    @Operation(summary = "Удалить карту")
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<?> deleteCard(
            @Parameter(description = "UUID карты", required = true) @PathVariable UUID id);

    /**
     * Получает список всех запросов на блокировку карт, ожидающих обработки.
     *
     * @return Список DTO-объектов {@link AdminCardBlockRequestDto}, содержащих информацию о запросах на блокировку.
     */
    @Operation(summary = "Получить список запросов на блокировку карты")
    @GetMapping("/cards/block-requests")
    public List<AdminCardBlockRequestDto> pendingBlockRequests();
}