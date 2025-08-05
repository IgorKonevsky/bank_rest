package com.example.bank_rest.controller.impl;

import com.example.bank_rest.controller.CustomerController;
import com.example.bank_rest.dto.card.*;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService;


    @Override
    public Page<CardResponseDto> getCardsByCustomerId(User user, Pageable pageable, String cardNumber) {
        log.info("Current User: {}", user.toString());
        return customerService.getCardsByCustomer(user.getId(), cardNumber, pageable);
    }

    @Override
    public TransferResponseDto transfer(User user, TransferRequestDto transferRequestDto) {

        return customerService.transfer(user.getId(), transferRequestDto);
    }

    @Override
    public OpenedBlockRequestDto requestBlock(User user, BlockRequestDto blockRequestDto) {
        return customerService.requestBlock(user.getId(), blockRequestDto);
    }
}
