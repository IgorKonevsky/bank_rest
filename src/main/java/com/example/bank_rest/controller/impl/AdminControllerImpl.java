package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.AdminController;
import com.example.bank_rest.dto.card.AdminCardBlockRequestDto;
import com.example.bank_rest.dto.card.CardAdminResponseDto;
import com.example.bank_rest.dto.card.CreateCardRequestDto;
import com.example.bank_rest.dto.card.UpdateCardDto;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @Override
    public List<User> getAdminList() {
        log.info("Class: AdminControllerImpl, method: getAdminList()");
        return adminService.getAdminList();
    }

    @Override
    public List<CardAdminResponseDto> getAllCards() {
        return adminService.getAllCards();
    }

    @Override
    public CardAdminResponseDto getCardById(UUID id) {
        return adminService.getCard(id);
    }

    @Override
    public CardAdminResponseDto createCard(CreateCardRequestDto createCardRequestDto) {
        return adminService.createCard(createCardRequestDto);
    }

    @Override
    public CardAdminResponseDto updateCard(UpdateCardDto updateCardDto) {
        return adminService.updateCard(updateCardDto);
    }

    @Override
    public ResponseEntity<?> deleteCard(UUID id) {
        adminService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<AdminCardBlockRequestDto> pendingBlockRequests() {
        return adminService.getPendingBlockRequests();
    }
}
