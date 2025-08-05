package com.example.bank_rest.util;

import java.util.Random;

public class CardNumberGenerator {
    public static String generateCardNumber(String iinPrefix) {
        if (iinPrefix.length() != 6 || !iinPrefix.matches("\\d{6}")) {
            throw new IllegalArgumentException("Invalid IIN prefix");
        }

        StringBuilder cardNumber = new StringBuilder(iinPrefix);

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            cardNumber.append(random.nextInt(10));
        }

        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private static int calculateLuhnCheckDigit(String numberWithoutCheckDigit) {
        int sum = 0;
        boolean alternate = true;

        for (int i = numberWithoutCheckDigit.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numberWithoutCheckDigit.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }
}
