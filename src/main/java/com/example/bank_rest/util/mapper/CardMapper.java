package com.example.bank_rest.util.mapper;

import com.example.bank_rest.dto.card.*;
import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.User;
import org.mapstruct.*;

import java.util.UUID;

/**
 * Интерфейс MapStruct для преобразования сущностей {@link Card} и {@link CardBlockRequest}
 * в соответствующие DTO и обратно.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {
    /**
     * Преобразует сущность {@link Card} в DTO {@link CardResponseDto}.
     * <p>
     *
     * @param card Исходная сущность карты.
     * @return DTO {@link CardResponseDto}.
     */
    @Mappings({
            @Mapping(target = "ownerName", source = "owner", qualifiedByName = "userFullName")
    })
    CardResponseDto toCardResponseDto(Card card);

    /**
     * Преобразует сущность {@link Card} в DTO {@link CardAdminResponseDto}.
     * <p>
     *
     * @param card Исходная сущность карты.
     * @return DTO {@link CardAdminResponseDto}.
     */
    @Mappings({
            @Mapping(target = "ownerName", source = "owner", qualifiedByName = "userFullName")
    })
    CardAdminResponseDto toCardAdminResponseDto(Card card);

    /**
     * Преобразует сущность {@link CardBlockRequest} в DTO {@link OpenedBlockRequestDto}.
     * <p>
     *
     * @param cardBlockRequest Исходная сущность запроса на блокировку карты.
     * @return DTO {@link OpenedBlockRequestDto}.
     */
    @Mappings({
            @Mapping(target = "cardNumber", source = "card", qualifiedByName = "cardNumberMasked"),
            @Mapping(target = "blockRequestStatus", source = "status"),
            @Mapping(target = "blockRequestDateTime", source = "requestedAt")
    })
    OpenedBlockRequestDto toOpenedBlockRequestDto(CardBlockRequest cardBlockRequest);

    /**
     * Преобразует сущность {@link CardBlockRequest} в DTO {@link AdminCardBlockRequestDto}.
     * <p>
     *
     * @param cardBlockRequest Исходная сущность запроса на блокировку карты.
     * @return DTO {@link AdminCardBlockRequestDto}.
     */
    @Mappings({
            @Mapping(target = "cardId", source = "card", qualifiedByName = "cardId"),
            @Mapping(target = "cardNumber", source = "card", qualifiedByName = "cardNumberMasked")
    })
    AdminCardBlockRequestDto toAdminCardBlockRequestDto(CardBlockRequest cardBlockRequest);

    /**
     * Обновляет существующую сущность {@link Card} данными из DTO {@link UpdateCardDto}.
     * <p>
     *
     * @param dto  DTO, содержащий обновленные данные.
     * @param card Целевая сущность карты, которую нужно обновить.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardFromDto(UpdateCardDto dto, @MappingTarget Card card);

    /**
     * Именованный метод для получения полного имени пользователя.
     * <p>
     * Используется в других методах маппера через атрибут {@code qualifiedByName}.
     *
     * @param user Сущность пользователя.
     * @return Полное имя пользователя.
     */
    @Named("userFullName")
    default String mapUserToFullName(User user) {
        return user.getFullName();
    }

    /**
     * Именованный метод для получения замаскированного номера карты.
     * <p>
     * Используется в других методах маппера через атрибут {@code qualifiedByName}.
     *
     * @param card Сущность карты.
     * @return Замаскированный номер карты.
     */
    @Named("cardNumberMasked")
    default String mapCardNumberMasked(Card card) {
        return card.getCardNumber();
    }

    /**
     * Именованный метод для получения ID карты.
     * <p>
     * Используется в других методах маппера через атрибут {@code qualifiedByName}.
     *
     * @param card Сущность карты.
     * @return ID карты.
     */
    @Named("cardId")
    default UUID mapCardId(Card card) {
        return card.getId();
    }
}