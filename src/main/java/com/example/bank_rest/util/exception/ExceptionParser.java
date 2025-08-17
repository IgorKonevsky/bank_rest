package com.example.bank_rest.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Утилитарный класс для обработки исключений.
 */
public class ExceptionParser {

    /**
     * Приватный конструктор, чтобы предотвратить создание экземпляров класса.
     */
    private ExceptionParser() {
    }

    /**
     * Преобразует трассировку стека исключения в строку.
     * <p>
     * Этот метод создает {@link StringWriter} и {@link PrintWriter},
     * чтобы записать в них полную трассировку стека переданного исключения,
     * а затем возвращает ее в виде строки.
     *
     * @param exception Исключение, трассировку стека которого нужно преобразовать.
     * @return Строка, содержащая полную трассировку стека исключения.
     */
    public static String makeStringFromStackTrace(Throwable exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}