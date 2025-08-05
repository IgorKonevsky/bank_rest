package com.example.bank_rest.exception.handler;

import com.example.bank_rest.exception.BalanceInsufficientException;
import com.example.bank_rest.exception.DataMissingException;
import com.example.bank_rest.util.exception.ExceptionParser;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataMissingException.class, BalanceInsufficientException.class, UsernameNotFoundException.class})
    public ErrorResponse badRequestHandler(Exception e) {
        log.debug("Ошибка 400, сообщение об ошибке: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResponse allHandler(Throwable e) {
        log.warn(ExceptionParser.makeStringFromStackTrace(e));
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
