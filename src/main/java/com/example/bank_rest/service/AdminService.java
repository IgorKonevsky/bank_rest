package com.example.bank_rest.service;

import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.User;

import java.util.List;
import java.util.UUID;


public interface AdminService {

    List<User> getAdminList();

    public CardAdminResponseDto getCard(UUID id);

    public List<CardAdminResponseDto> getAllCards();

    public CardAdminResponseDto createCard(CreateCardRequestDto createCardRequestDto);

    public CardAdminResponseDto updateCard(UpdateCardDto updateCardDto);

    void deleteCard(UUID id);

    public List<AdminCardBlockRequestDto> getPendingBlockRequests();
}
