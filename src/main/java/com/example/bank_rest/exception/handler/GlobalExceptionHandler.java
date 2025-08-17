package com.example.bank_rest.exception.handler;

import com.example.bank_rest.exception.BalanceInsufficientException;
import com.example.bank_rest.exception.DataMissingException;
import com.example.bank_rest.util.exception.ExceptionParser;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для REST API.
 * <p>
 * Этот класс перехватывает различные исключения, возникающие в приложении,
 * и возвращает стандартизированные ответы об ошибках в формате JSON.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения, связанные с ошибками валидации аргументов метода.
     * <p>
     * Это исключение выбрасывается, когда аргумент контроллера, аннотированный
     * {@code @Valid}, не проходит валидацию. Метод собирает все ошибки полей
     * и возвращает ответ с HTTP-статусом 400 Bad Request.
     *
     * @param ex      Исключение {@link MethodArgumentNotValidException}.
     * @param request Текущий веб-запрос.
     * @return {@link ResponseEntity} с объектом {@link ErrorResponse} и статусом 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors,
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("Validation error: {}, path: {}", errors, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключения, связанные с нарушениями ограничений (constraints).
     * <p>
     * Это исключение выбрасывается, когда происходит нарушение ограничений,
     * определенных в сущностях или DTO. Метод собирает все нарушения
     * и возвращает ответ с HTTP-статусом 400 Bad Request.
     *
     * @param ex      Исключение {@link ConstraintViolationException}.
     * @param request Текущий веб-запрос.
     * @return {@link ResponseEntity} с объектом {@link ErrorResponse} и статусом 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Constraint violation",
                errors,
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("Constraint violation: {}, path: {}", errors, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает общие исключения, приводящие к 400 Bad Request.
     * <p>
     * Этот обработчик перехватывает несколько типов исключений, которые
     * сигнализируют о некорректных данных в запросе, например,
     * {@link DataMissingException}, {@link BalanceInsufficientException} и {@link UsernameNotFoundException}.
     *
     * @param ex      Исключение.
     * @param request Текущий веб-запрос.
     * @return {@link ResponseEntity} с объектом {@link ErrorResponse} и статусом 400.
     */
    @ExceptionHandler({DataMissingException.class, BalanceInsufficientException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null,
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("Bad request error: {}, path: {}", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключения, связанные с некорректным форматом JSON в теле запроса.
     * <p>
     * Это исключение выбрасывается, когда Spring не может корректно разобрать
     * JSON-данные из-за синтаксических ошибок.
     *
     * @param ex      Исключение {@link HttpMessageNotReadableException}.
     * @param request Текущий веб-запрос.
     * @return {@link ResponseEntity} с объектом {@link ErrorResponse} и статусом 400.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid JSON format",
                null,
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("JSON parse error: {}, path: {}", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает все остальные, необработанные исключения.
     * <p>
     * Этот метод действует как "последний рубеж" для обработки всех
     * непредвиденных исключений. Он возвращает ответ с HTTP-статусом 500
     * Internal Server Error и логирует полный стек вызовов для отладки.
     *
     * @param ex      Любое необработанное исключение.
     * @param request Текущий веб-запрос.
     * @return {@link ResponseEntity} с объектом {@link ErrorResponse} и статусом 500.
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Throwable ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                null,
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("Unexpected error: {}, path: {}, stacktrace: {}",
                ex.getMessage(), request.getDescription(false), ExceptionParser.makeStringFromStackTrace(ex));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}