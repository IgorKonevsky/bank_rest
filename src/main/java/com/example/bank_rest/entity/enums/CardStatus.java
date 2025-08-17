package com.example.bank_rest.entity.enums;

/**
 * Перечисление, определяющее возможные статусы банковской карты.
 */
public enum CardStatus {
    /**
     * Карта активна и может быть использована.
     */
    ACTIVE,
    /**
     * Карта заблокирована.
     */
    BLOCKED,
    /**
     * Срок действия карты истек.
     */
    EXPIRED,
    /**
     * Карта удалена.
     */
    DELETED
}