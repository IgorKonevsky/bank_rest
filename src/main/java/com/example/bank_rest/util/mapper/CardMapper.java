package com.example.bank_rest.util.mapper;

import com.example.bank_rest.dto.card.*;
import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.User;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {
    @Mappings({
            @Mapping(target = "ownerName", source = "owner", qualifiedByName = "userFullName")
    })
    CardResponseDto toCardResponseDto(Card card);

    @Mappings({
            @Mapping(target = "ownerName", source = "owner", qualifiedByName = "userFullName")
    })
    CardAdminResponseDto toCardAdminResponseDto(Card card);

    @Mappings({
            @Mapping(target = "cardNumber", source = "card", qualifiedByName = "cardNumberMasked"),
            @Mapping(target = "blockRequestStatus", source = "status"),
            @Mapping(target = "blockRequestDateTime", source = "requestedAt")
    })
    OpenedBlockRequestDto toOpenedBlockRequestDto(CardBlockRequest cardBlockRequest);

    @Mappings({
            @Mapping(target = "cardId", source = "card", qualifiedByName = "cardId"),
            @Mapping(target = "cardNumber", source = "card", qualifiedByName = "cardNumberMasked")
    })
    AdminCardBlockRequestDto toAdminCardBlockRequestDto(CardBlockRequest cardBlockRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardFromDto(UpdateCardDto dto, @MappingTarget Card card);

    @Named("userFullName")
    default String mapUserToFullName(User user) {
        return user.getFullName();
    }

    @Named("cardNumberMasked")
    default String mapCardNumberMasked(Card card) {
        return card.getCardNumber();
    }

    @Named("cardId")
    default UUID mapCardId(Card card) {
        return card.getId();
    }


}
