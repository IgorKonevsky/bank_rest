package com.example.bank_rest.entity;


import com.example.bank_rest.entity.enums.BlockRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность, представляющая запрос на блокировку карты.
 * <p>
 * Этот класс сопоставляется с таблицей `card_block_requests` в базе данных.
 */
@Entity
@Table(name = "card_block_requests")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CardBlockRequest {
    /**
     * Уникальный идентификатор (UUID) запроса на блокировку.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * Текущий статус запроса на блокировку.
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BlockRequestStatus status;
    /**
     * Дата и время создания запроса.
     */
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
    /**
     * Карта, для которой был сделан запрос на блокировку. Связь {@link ManyToOne} с сущностью {@link Card}.
     */
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}