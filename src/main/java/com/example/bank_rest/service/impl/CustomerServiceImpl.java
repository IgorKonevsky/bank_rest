package com.example.bank_rest.service.impl;

import com.example.bank_rest.dto.card.*;
import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.enums.BlockRequestStatus;
import com.example.bank_rest.entity.enums.CardStatus;
import com.example.bank_rest.exception.BalanceInsufficientException;
import com.example.bank_rest.exception.DataMissingException;
import com.example.bank_rest.repository.CardBlockRequestRepository;
import com.example.bank_rest.repository.CardRepository;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.CustomerService;
import com.example.bank_rest.util.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardBlockRequestRepository cardBlockRequestRepository;
    private final CardMapper cardMapper;

    @Override
    public Page<CardResponseDto> getCardsByCustomer(UUID id, String cardNumber, Pageable pageable) {
        Page<Card> cards = cardRepository.findAllByOwnerIdAndLastFourNumbersContaining(id, cardNumber, pageable);
        return cards.map(cardMapper::toCardResponseDto);
    }

    @Override
    public TransferResponseDto transfer(UUID ownerId, TransferRequestDto transferRequestDto) {
        Card sourceCard = cardRepository
                .findByOwnerIdAndLastFourNumbers(ownerId, transferRequestDto.sourceCardLastFourNumbers())
                .orElseThrow(() -> new DataMissingException("No source card found"));
        Card destinationCard = cardRepository
                .findByOwnerIdAndLastFourNumbers(ownerId, transferRequestDto.destinationCardLastFourNumbers())
                .orElseThrow(() -> new DataMissingException("No destination card found"));

        if (sourceCard.getId().equals(destinationCard.getId())) {
            throw new IllegalArgumentException("Source and destination card IDs are the same");
        }
        if (sourceCard.getBalance().compareTo(transferRequestDto.amount()) < 0) {
            throw new BalanceInsufficientException("Insufficient balance");
        }

        sourceCard.setBalance(sourceCard.getBalance().subtract(transferRequestDto.amount()));
        destinationCard.setBalance(destinationCard.getBalance().add(transferRequestDto.amount()));

        cardRepository.save(sourceCard);
        cardRepository.save(destinationCard);
        return new TransferResponseDto(
                sourceCard.getCardNumber(),
                sourceCard.getBalance(),
                destinationCard.getCardNumber(),
                destinationCard.getBalance(),
                transferRequestDto.amount()
        );
    }

    @Override
    public OpenedBlockRequestDto requestBlock(UUID ownerId, BlockRequestDto blockRequestDto) {
        log.info("Class: CustomerServiceImpl, method: requestBlock, blockRequestDto: {}", blockRequestDto);
        Card card = cardRepository
                .findByOwnerIdAndLastFourNumbersAndCardStatus(
                        ownerId, blockRequestDto.lastFourNumbers(), CardStatus.ACTIVE)
                .orElseThrow(() -> new DataMissingException("No card found"));
        CardBlockRequest cardBlockRequest = new CardBlockRequest();
        cardBlockRequest.setCard(card);
        cardBlockRequest.setRequestedAt(LocalDateTime.now());
        cardBlockRequest.setStatus(BlockRequestStatus.REQUESTED);
        cardBlockRequestRepository.save(cardBlockRequest);
        return cardMapper.toOpenedBlockRequestDto(cardBlockRequest);

    }
}
