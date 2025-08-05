package com.example.bank_rest.service;

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
import com.example.bank_rest.service.impl.CustomerServiceImpl;
import com.example.bank_rest.util.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardBlockRequestRepository cardBlockRequestRepository;
    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private UUID userId;
    private Card card1;
    private Card card2;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        card1 = Card.builder()
                .id(UUID.randomUUID())
                .lastFourNumbers("1234")
                .balance(BigDecimal.valueOf(500))
                .cardStatus(CardStatus.ACTIVE)
                .expiryDate(LocalDate.now().plusYears(2))
                .build();
        card2 = Card.builder()
                .id(UUID.randomUUID())
                .lastFourNumbers("5678")
                .balance(BigDecimal.valueOf(200))
                .cardStatus(CardStatus.ACTIVE)
                .expiryDate(LocalDate.now().plusYears(2))
                .build();
    }

    @Test
    void getCardsByCustomer_returnsPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Card> cardPage = new PageImpl<>(List.of(card1));
        CardResponseDto dto = new CardResponseDto("John Doe", "**** **** **** 1234", LocalDate.now(), CardStatus.ACTIVE, BigDecimal.valueOf(500));

        when(cardRepository.findAllByOwnerIdAndLastFourNumbersContaining(userId, "1234", pageable))
                .thenReturn(cardPage);
        when(cardMapper.toCardResponseDto(card1)).thenReturn(dto);

        Page<CardResponseDto> result = customerService.getCardsByCustomer(userId, "1234", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("John Doe", result.getContent().get(0).ownerName());
    }

    @Test
    void transfer_successful() {
        TransferRequestDto request = new TransferRequestDto("1234", "5678", BigDecimal.valueOf(100));

        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "1234"))
                .thenReturn(Optional.of(card1));
        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "5678"))
                .thenReturn(Optional.of(card2));

        TransferResponseDto response = customerService.transfer(userId, request);

        assertEquals(BigDecimal.valueOf(400), card1.getBalance());
        assertEquals(BigDecimal.valueOf(300), card2.getBalance());

        verify(cardRepository, times(1)).save(card1);
        verify(cardRepository, times(1)).save(card2);
        assertEquals(request.amount(), response.transferredAmount());
    }

    @Test
    void transfer_insufficientBalance_throwsException() {
        card1.setBalance(BigDecimal.valueOf(50));
        TransferRequestDto request = new TransferRequestDto("1234", "5678", BigDecimal.valueOf(100));

        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "1234"))
                .thenReturn(Optional.of(card1));
        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "5678"))
                .thenReturn(Optional.of(card2));

        assertThrows(BalanceInsufficientException.class, () -> customerService.transfer(userId, request));
    }

    @Test
    void transfer_sameCardIds_throwsException() {
        card2.setId(card1.getId());
        TransferRequestDto request = new TransferRequestDto("1234", "5678", BigDecimal.valueOf(100));

        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "1234"))
                .thenReturn(Optional.of(card1));
        when(cardRepository.findByOwnerIdAndLastFourNumbers(userId, "5678"))
                .thenReturn(Optional.of(card2));

        assertThrows(IllegalArgumentException.class, () -> customerService.transfer(userId, request));
    }

    @Test
    void requestBlock_successful() {
        BlockRequestDto requestDto = new BlockRequestDto("1234");
        CardBlockRequest savedRequest = new CardBlockRequest();
        savedRequest.setCard(card1);
        savedRequest.setStatus(BlockRequestStatus.REQUESTED);
        savedRequest.setRequestedAt(LocalDateTime.now());

        OpenedBlockRequestDto expectedDto = new OpenedBlockRequestDto(
                card1.getCardNumber(), BlockRequestStatus.REQUESTED, savedRequest.getRequestedAt());

        when(cardRepository.findByOwnerIdAndLastFourNumbersAndCardStatus(userId, "1234", CardStatus.ACTIVE))
                .thenReturn(Optional.of(card1));
        when(cardBlockRequestRepository.save(any(CardBlockRequest.class)))
                .thenReturn(savedRequest);
        when(cardMapper.toOpenedBlockRequestDto(any(CardBlockRequest.class)))
                .thenReturn(expectedDto);

        OpenedBlockRequestDto result = customerService.requestBlock(userId, requestDto);

        assertEquals(expectedDto.cardNumber(), result.cardNumber());
        assertEquals(expectedDto.blockRequestStatus(), result.blockRequestStatus());
    }

    @Test
    void requestBlock_cardNotFound_throwsException() {
        BlockRequestDto requestDto = new BlockRequestDto("1234");

        when(cardRepository.findByOwnerIdAndLastFourNumbersAndCardStatus(userId, "1234", CardStatus.ACTIVE))
                .thenReturn(Optional.empty());

        assertThrows(DataMissingException.class, () -> customerService.requestBlock(userId, requestDto));
    }
}
