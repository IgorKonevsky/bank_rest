package com.example.bank_rest.dto.card;

import com.example.bank_rest.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MaskedCardDto(UUID id, String lastFourNumbers, LocalDate expiryDate,
                            CardStatus cardStatus, BigDecimal balance) {
    public String getCardNumber() {
        return "**** **** ****" + lastFourNumbers;
    }
}
