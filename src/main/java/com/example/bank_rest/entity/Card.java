package com.example.bank_rest.entity;

import com.example.bank_rest.entity.enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Сущность, представляющая банковскую карту.
 * <p>
 * Этот класс сопоставляется с таблицей `cards` в базе данных.
 */
@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Card {
    /**
     * Уникальный идентификатор (UUID) карты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Первые двенадцать цифр номера карты.
     */
    @Column(name = "first_twelve_numbers", nullable = false)
    private String firstTwelveNumbers;
    /**
     * Последние четыре цифры номера карты.
     */
    @Column(name = "last_four_numbers", nullable = false)
    private String lastFourNumbers;
    /**
     * Дата окончания срока действия карты.
     */
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    /**
     * Текущий статус карты (например, ACTIVE, BLOCKED).
     */
    @Column(name = "card_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    /**
     * Текущий баланс карты.
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    /**
     * Владелец карты. Связь {@link ManyToOne} с сущностью {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    /**
     * Возвращает полный номер карты в замаскированном формате.
     *
     * @return Строка, содержащая замаскированный номер карты.
     */
    public String getCardNumber() {
        return "**** **** **** " + lastFourNumbers;
    }

}