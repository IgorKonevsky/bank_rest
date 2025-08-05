package com.example.bank_rest.service.impl;

import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.entity.enums.BlockRequestStatus;
import com.example.bank_rest.entity.enums.CardStatus;
import com.example.bank_rest.exception.DataMissingException;
import com.example.bank_rest.repository.CardBlockRequestRepository;
import com.example.bank_rest.repository.CardRepository;
import com.example.bank_rest.repository.RoleRepository;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.AdminService;
import com.example.bank_rest.util.CardNumberGenerator;
import com.example.bank_rest.util.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CardRepository cardRepository;
    private final CardBlockRequestRepository cardBlockRequestRepository;
    private final CardMapper cardMapper;

    @Override
    public List<User> getAdminList() {
        List<User> adminList = userRepository.findAllByRolesContains(roleRepository.findRoleByName("ROLE_ADMIN"));
        log.info("Class: AdminServiceImpl, method: getAdminList, adminList: {}", adminList.toString());
        return adminList;
    }

    @Override
    public CardAdminResponseDto getCard(UUID id) {
        return cardRepository.findById(id).map(cardMapper::toCardAdminResponseDto).orElseThrow(() -> new DataMissingException("Card Not Found"));
    }

    @Override
    public List<CardAdminResponseDto> getAllCards() {
        List<Card> allCards = cardRepository.findAll();
        return allCards.stream().map(cardMapper::toCardAdminResponseDto).toList();
    }


    @Override
    public CardAdminResponseDto createCard(CreateCardRequestDto createCardRequestDto) {
        String fullCardNumber = CardNumberGenerator.generateCardNumber("400000");
        String firstTwelveNumbers = fullCardNumber.substring(0, 12);
        String lastFourNumbers = fullCardNumber.substring(12);
        Card newCard = Card.builder()
                .owner(userRepository.findById(createCardRequestDto.ownerId()).orElseThrow())
                .balance(BigDecimal.valueOf(0))
                .expiryDate(createCardRequestDto.expiryDate())
                .firstTwelveNumbers(firstTwelveNumbers)
                .lastFourNumbers(lastFourNumbers)
                .cardStatus(CardStatus.ACTIVE)
                .build();
        log.info("Class: AdminServiceImpl, method: createCard, newCard: {}", newCard.toString());
        cardRepository.save(newCard);
        return cardMapper.toCardAdminResponseDto(newCard);
    }

    @Override
    public CardAdminResponseDto updateCard(UpdateCardDto updateCardDto) {
        Card card = cardRepository.findById(updateCardDto.id()).orElseThrow(() -> new DataMissingException("Card is not found"));
        cardMapper.updateCardFromDto(updateCardDto, card);
        cardRepository.save(card);
        return cardMapper.toCardAdminResponseDto(card);
    }

    @Override
    public void deleteCard(UUID id) {
        cardRepository.findById(id)
                .ifPresent(card -> {
                    card.setCardStatus(CardStatus.DELETED);
                    cardRepository.save(card);
                });
    }

    @Override
    public List<AdminCardBlockRequestDto> getPendingBlockRequests() {
        List<CardBlockRequest> cardBlockRequestList = cardBlockRequestRepository
                .findAllByStatus(BlockRequestStatus.REQUESTED);
        return cardBlockRequestList.stream().map(cardMapper::toAdminCardBlockRequestDto).toList();

    }
}
