package com.example.bank_rest.service;

import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.entity.enums.BlockRequestStatus;
import com.example.bank_rest.entity.enums.CardStatus;
import com.example.bank_rest.repository.CardBlockRequestRepository;
import com.example.bank_rest.repository.CardRepository;
import com.example.bank_rest.repository.RoleRepository;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.impl.AdminServiceImpl;
import com.example.bank_rest.util.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardBlockRequestRepository cardBlockRequestRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    private User testUser;
    private Card testCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(UUID.randomUUID(), "John", "Doe", "Middle", "john.doe", "password", null);
        testCard = new Card(UUID.randomUUID(), "123456789012", "3456", LocalDate.now().plusYears(1), CardStatus.ACTIVE, BigDecimal.valueOf(100), testUser);
    }

    @Test
    void testGetAdminList() {
        when(userRepository.findAllByRolesContains(any())).thenReturn(List.of(testUser));

        List<User> adminList = adminService.getAdminList();

        assertNotNull(adminList);
        assertEquals(1, adminList.size());
        assertEquals(testUser.getUsername(), adminList.get(0).getUsername());
    }

    @Test
    void testGetCard() {
        UUID cardId = testCard.getId();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(testCard));
        when(cardMapper.toCardAdminResponseDto(testCard)).thenReturn(new CardAdminResponseDto(testCard.getId(), testCard.getOwner().getFullName(), testCard.getCardNumber(), testCard.getExpiryDate(), testCard.getCardStatus(), testCard.getBalance()));

        CardAdminResponseDto response = adminService.getCard(cardId);

        assertNotNull(response);
        assertEquals(testCard.getId(), response.id());
    }

    @Test
    void testGetAllCards() {
        when(cardRepository.findAll()).thenReturn(List.of(testCard));
        when(cardMapper.toCardAdminResponseDto(testCard)).thenReturn(new CardAdminResponseDto(testCard.getId(), testCard.getOwner().getFullName(), testCard.getCardNumber(), testCard.getExpiryDate(), testCard.getCardStatus(), testCard.getBalance()));

        List<CardAdminResponseDto> cards = adminService.getAllCards();

        assertNotNull(cards);
        assertEquals(1, cards.size());
    }

    @Test
    void testCreateCard() {
        UUID testCardId = UUID.fromString("7a189ebf-bbb7-4cf4-9c16-bea8ffba273c");
        CreateCardRequestDto createCardRequestDto = new CreateCardRequestDto(testUser.getId(), LocalDate.now().plusYears(1));

        when(userRepository.findById(createCardRequestDto.ownerId())).thenReturn(Optional.of(testUser));

        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            card.setId(testCardId);
            return card;
        });

        when(cardMapper.toCardAdminResponseDto(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            return new CardAdminResponseDto(
                    card.getId(),
                    card.getOwner().getFullName(),
                    card.getCardNumber(),
                    card.getExpiryDate(),
                    card.getCardStatus(),
                    card.getBalance()
            );
        });

        CardAdminResponseDto response = adminService.createCard(createCardRequestDto);

        assertNotNull(response);
        assertEquals(testCardId, response.id());
    }

    @Test
    void testUpdateCard() {
        UpdateCardDto updateCardDto = new UpdateCardDto(testCard.getId(), CardStatus.BLOCKED, BigDecimal.valueOf(50));

        when(cardRepository.findById(testCard.getId())).thenReturn(Optional.of(testCard));

        doAnswer(invocation -> {
            UpdateCardDto dto = invocation.getArgument(0);
            Card card = invocation.getArgument(1);
            card.setCardStatus(dto.cardStatus());
            card.setBalance(dto.balance());
            return null;
        }).when(cardMapper).updateCardFromDto(any(UpdateCardDto.class), any(Card.class));

        when(cardMapper.toCardAdminResponseDto(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            return new CardAdminResponseDto(
                    card.getId(),
                    card.getOwner().getFullName(),
                    card.getCardNumber(),
                    card.getExpiryDate(),
                    card.getCardStatus(),
                    card.getBalance()
            );
        });

        CardAdminResponseDto response = adminService.updateCard(updateCardDto);

        assertNotNull(response);
        assertEquals(CardStatus.BLOCKED, response.cardStatus());
        assertEquals(BigDecimal.valueOf(50), response.balance());
    }

    @Test
    void testDeleteCard() {
        UUID cardId = testCard.getId();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(testCard));

        adminService.deleteCard(cardId);

        verify(cardRepository, times(1)).save(testCard);  // теперь save вызывается
    }

    @Test
    void testGetPendingBlockRequests() {
        CardBlockRequest cardBlockRequest = new CardBlockRequest(UUID.randomUUID(), BlockRequestStatus.REQUESTED, LocalDate.now().atStartOfDay(), testCard);
        when(cardBlockRequestRepository.findAllByStatus(BlockRequestStatus.REQUESTED)).thenReturn(List.of(cardBlockRequest));
        when(cardMapper.toAdminCardBlockRequestDto(cardBlockRequest)).thenReturn(new AdminCardBlockRequestDto(cardBlockRequest.getId(), cardBlockRequest.getStatus(), cardBlockRequest.getRequestedAt(), cardBlockRequest.getCard().getId(), cardBlockRequest.getCard().getCardNumber()));

        List<AdminCardBlockRequestDto> blockRequests = adminService.getPendingBlockRequests();

        assertNotNull(blockRequests);
        assertEquals(1, blockRequests.size());
    }
}
