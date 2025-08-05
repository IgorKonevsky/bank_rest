package com.example.bank_rest.exception;

public class DataMissingException extends RuntimeException{
    public DataMissingException(String message) {
        super(message);
    }
}
