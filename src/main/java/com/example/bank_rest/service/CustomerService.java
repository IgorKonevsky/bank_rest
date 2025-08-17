package com.example.bank_rest.service;

import com.example.bank_rest.dto.card.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Интерфейс сервиса для операций клиента.
 * Определяет методы, доступные для обычных пользователей, такие как просмотр карт, переводы и запросы на блокировку.
 */
public interface CustomerService {

    /**
     * Получает страницу карт для указанного клиента.
     *
     * @param customerId Уникальный идентификатор клиента.
     * @param cardNumberLastFourNumbers Последние 4 цифры номера карты для фильтрации.
     * @param pageable Параметры пагинации и сортировки.
     * @return Страница DTO-объектов {@link CardResponseDto}.
     */
    Page<CardResponseDto> getCardsByCustomer(UUID customerId, String cardNumberLastFourNumbers, Pageable pageable);

    /**
     * Выполняет перевод средств между двумя картами клиента.
     *
     * @param userId Уникальный идентификатор пользователя.
     * @param transferRequestDto DTO, содержащий данные для перевода.
     * @return DTO {@link TransferResponseDto} с информацией о результате перевода.
     */
    TransferResponseDto transfer(UUID userId, TransferRequestDto transferRequestDto);

    /**
     * Создает запрос на блокировку карты для указанного пользователя.
     *
     * @param userId Уникальный идентификатор пользователя.
     * @param blockRequestDto DTO, содержащий данные о карте для блокировки.
     * @return DTO {@link OpenedBlockRequestDto} с информацией о созданном запросе.
     */
    OpenedBlockRequestDto requestBlock(UUID userId, BlockRequestDto blockRequestDto);
}