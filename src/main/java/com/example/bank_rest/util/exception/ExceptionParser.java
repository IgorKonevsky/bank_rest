package com.example.bank_rest.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionParser {
    ExceptionParser() {
    }

    public static String makeStringFromStackTrace(Throwable exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
