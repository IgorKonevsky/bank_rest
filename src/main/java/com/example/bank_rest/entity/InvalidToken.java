package com.example.bank_rest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Сущность, представляющая недействительный (аннулированный) JWT-токен.
 * <p>
 * Этот класс сопоставляется с таблицей `invalid_tokens` в базе данных.
 * Используется для хранения токенов, которые были аннулированы при выходе пользователя из системы.
 */
@Entity
@Table(name = "invalid_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidToken {
    /**
     * Аннулированный токен. Используется как первичный ключ.
     */
    @Id
    private String token;
    /**
     * Дата, когда срок действия токена истечет.
     */
    private Date expiryDate;
}