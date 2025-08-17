package com.example.bank_rest.exception;

/**
 * Исключение, выбрасываемое, когда отсутствуют необходимые данные.
 */
public class DataMissingException extends RuntimeException{
    /**
     * Конструктор для создания исключения с указанным сообщением.
     *
     * @param message Сообщение, описывающее причину исключения.
     */
    public DataMissingException(String message) {
        super(message);
    }
}