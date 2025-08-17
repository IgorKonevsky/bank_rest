package com.example.bank_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Сущность, представляющая роль пользователя в системе (например, 'ADMIN', 'CUSTOMER').
 * <p>
 * Эта сущность сопоставляется с таблицей `roles` в базе данных.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    /**
     * Уникальный идентификатор (UUID) роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название роли. Должно быть уникальным.
     */
    @Column(unique = true)
    private String name;

    /**
     * Конструктор для создания новой роли с указанным именем.
     *
     * @param name Название роли.
     */
    public Role(String name) {
        this.name = name;
    }
}