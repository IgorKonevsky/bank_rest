package com.example.bank_rest.exception;

/**
 * Исключение, выбрасываемое, когда на балансе карты недостаточно средств для выполнения операции.
 */
public class BalanceInsufficientException extends RuntimeException {
    /**
     * Конструктор для создания исключения с указанным сообщением.
     *
     * @param message Сообщение, описывающее причину исключения.
     */
    public BalanceInsufficientException(String message) {
        super(message);
    }
}