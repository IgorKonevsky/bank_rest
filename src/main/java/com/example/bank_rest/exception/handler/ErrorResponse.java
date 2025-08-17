package com.example.bank_rest.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO, используемый для форматирования ответов об ошибках REST API.
 * <p>
 * Этот класс предоставляет стандартизированный формат для сообщений об ошибках,
 * который включает статус HTTP, описание ошибки, общее сообщение, детализированные
 * ошибки (если есть), путь запроса и временную метку.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    /**
     * Статус HTTP-ответа.
     */
    private final int status;
    /**
     * Краткое описание ошибки (например, "Bad Request").
     */
    private final String error;
    /**
     * Общее сообщение об ошибке.
     */
    private final String message;
    /**
     * Детализированные ошибки (например, ошибки валидации), где ключ — это имя поля, а значение — сообщение об ошибке.
     */
    private final Map<String, String> errors;
    /**
     * Путь запроса, который вызвал ошибку.
     */
    private final String path;
    /**
     * Временная метка, когда произошла ошибка.
     */
    private final LocalDateTime timestamp;

    /**
     * Конструктор для создания объекта {@code ErrorResponse}.
     *
     * @param status    Статус HTTP-ответа.
     * @param message   Общее сообщение об ошибке.
     * @param errors    Map с детализированными ошибками.
     * @param path      Путь запроса.
     * @param timestamp Временная метка.
     */
    public ErrorResponse(HttpStatus status, String message, Map<String, String> errors, String path, LocalDateTime timestamp) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.timestamp = timestamp;
    }
}