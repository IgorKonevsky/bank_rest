package com.example.bank_rest.repository;

import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Page<Card> findAllByOwnerId(UUID ownerId, Pageable pageable);

    Page<Card> findAllByOwnerIdAndLastFourNumbersContaining(UUID ownerId, String cardNumber, Pageable pageable);

    Optional<Card> findByOwnerIdAndLastFourNumbers(UUID ownerId, String lastFourNumbers);

    Optional<Card> findByOwnerIdAndLastFourNumbersAndCardStatus(
            UUID ownerId, String lastFourNumbers, CardStatus cardStatus);
}
