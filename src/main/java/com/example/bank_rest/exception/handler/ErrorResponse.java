package com.example.bank_rest.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> errors;
    private final String path;
    private final LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message, Map<String, String> errors, String path, LocalDateTime timestamp) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.timestamp = timestamp;
    }
}
