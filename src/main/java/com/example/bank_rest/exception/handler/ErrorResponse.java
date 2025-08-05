package com.example.bank_rest.exception.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final Integer code;
    private final String message;
    private final LocalDateTime timeStamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
