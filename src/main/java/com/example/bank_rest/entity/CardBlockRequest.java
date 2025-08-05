package com.example.bank_rest.entity;


import com.example.bank_rest.entity.enums.BlockRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_block_requests")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CardBlockRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BlockRequestStatus status;
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
