package com.example.bank_rest.service;

import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.User;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс сервиса для административных операций.
 * Определяет методы для управления пользователями и картами с правами администратора.
 */
public interface AdminService {

    /**
     * Получает список всех администраторов.
     *
     * @return Список объектов {@link User}, представляющих администраторов.
     */
    List<User> getAdminList();

    /**
     * Получает список всех карт в системе.
     *
     * @return Список DTO-объектов {@link CardAdminResponseDto} для всех карт.
     */
    List<CardAdminResponseDto> getAllCards();

    /**
     * Получает карту по ее уникальному идентификатору (UUID).
     *
     * @param id Уникальный идентификатор (UUID) карты.
     * @return DTO-объект {@link CardAdminResponseDto} для найденной карты.
     */
    CardAdminResponseDto getCard(UUID id);

    /**
     * Создает новую карту.
     *
     * @param createCardRequestDto DTO, содержащий данные для создания новой карты.
     * @return DTO-объект {@link CardAdminResponseDto} для созданной карты.
     */
    CardAdminResponseDto createCard(CreateCardRequestDto createCardRequestDto);

    /**
     * Обновляет существующую карту.
     *
     * @param updateCardDto DTO, содержащий данные для обновления карты, включая ее идентификатор.
     * @return DTO-объект {@link CardAdminResponseDto} для обновленной карты.
     */
    CardAdminResponseDto updateCard(UpdateCardDto updateCardDto);

    /**
     * Удаляет карту по ее уникальному идентификатору.
     *
     * @param id Уникальный идентификатор (UUID) карты, которую нужно удалить.
     */
    void deleteCard(UUID id);

    /**
     * Получает список всех запросов на блокировку карт, ожидающих обработки.
     *
     * @return Список DTO-объектов {@link AdminCardBlockRequestDto}, содержащих информацию о запросах на блокировку.
     */
    List<AdminCardBlockRequestDto> getPendingBlockRequests();
}