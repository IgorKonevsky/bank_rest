package com.example.bank_rest.service;

import com.example.bank_rest.dto.card.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerService {

    public Page<CardResponseDto> getCardsByCustomer(UUID id, String cardNumberFilter, Pageable pageable);

    @Transactional
    public TransferResponseDto transfer(UUID ownerId, TransferRequestDto transferRequestDto);

    public OpenedBlockRequestDto requestBlock(UUID ownerId, BlockRequestDto blockRequestDto);
}
