package com.example.bank_rest.entity;

import com.example.bank_rest.entity.enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_twelve_numbers", nullable = false)
    private String firstTwelveNumbers;
    @Column(name = "last_four_numbers", nullable = false)
    private String lastFourNumbers;
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Column(name = "card_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public String getCardNumber() {
        return "**** **** **** " + lastFourNumbers;
    }

}
