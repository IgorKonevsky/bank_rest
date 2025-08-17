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

/**
 * Интерфейс контроллера для операций, доступных клиенту.
 * <p>
 * Требует аутентификации с помощью JWT-токена, указанного в заголовке `Authorization`.
 */
@Tag(name = "Customer", description = "Операции клиента")
@RequestMapping("/api/v1/customer")
@SecurityRequirement(name = "bearerAuth")
public interface CustomerController {

    /**
     * Получает список карт, принадлежащих текущему клиенту, с возможностью пагинации, сортировки и фильтрации.
     *
     * @param user         Текущий аутентифицированный пользователь.
     * @param pageable     Параметры пагинации и сортировки.
     * @param cardNumber   Необязательный фильтр по последним 4 цифрам номера карты.
     * @return Страница с DTO-объектами карт.
     */
    @Operation(summary = "Получить карты клиента")
    @GetMapping("/cards")
    public Page<CardResponseDto> getCardsByCustomerId(
            @Parameter(description = "Текущий пользователь", hidden = true)
            @AuthenticationPrincipal User user,
            @Parameter(description = "Параметры пагинации и сортировки")
            @PageableDefault(size = 5, sort = "expiryDate") Pageable pageable,
            @Parameter(description = "Фильтр по последним 4 цифрам карты")
            @RequestParam(required = false, defaultValue = "") String cardNumber);

    /**
     * Выполняет перевод средств между двумя картами одного клиента.
     *
     * @param user             Текущий аутентифицированный пользователь.
     * @param transferRequestDto DTO, содержащий данные для перевода: карты-источник, карты-назначение и сумма.
     * @return DTO с информацией о выполненном переводе.
     */
    @Operation(summary = "Перевод между своими картами")
    @PostMapping("/transfer")
    public TransferResponseDto transfer(
            @Parameter(description = "Текущий пользователь", hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "Данные для перевода", required = true) @RequestBody TransferRequestDto transferRequestDto);

    /**
     * Отправляет запрос на блокировку карты.
     *
     * @param user             Текущий аутентифицированный пользователь.
     * @param blockRequestDto  DTO с данными о карте, которую нужно заблокировать.
     * @return DTO с информацией о созданном запросе на блокировку.
     */
    @Operation(summary = "Запрос блокировки карты")
    @PostMapping("/block")
    public OpenedBlockRequestDto requestBlock(
            @Parameter(description = "Текущий пользователь", hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "Данные запроса блокировки карты", required = true) @RequestBody BlockRequestDto blockRequestDto);
}