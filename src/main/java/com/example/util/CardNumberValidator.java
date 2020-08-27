package com.example.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardNumberValidator implements Validator {

    @Override
    public boolean validate(String cardNumber) {
        if (cardNumber == null || cardNumber.length() == 0) {
            return false;
        }

        List<Integer> digits;
        try {
            digits = Arrays.stream(cardNumber.split(""))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return false;
        }

        if (digits.size() != 16) {
            return false;
        }

        digits = multiplyOddDigits(digits);
        digits = reduceByNine(digits);
        int sum = sumDigits(digits);

        if (sum % 10 == 0) {
            return true;
        }

        return false;
    }

    private List<Integer> multiplyOddDigits(List<Integer> digits) {
        int size = digits.size() - 1;
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                digits.set(i, digits.get(i) * 2);
            }
        }
        return digits;
    }

    private List<Integer> reduceByNine(List<Integer> digits) {
        int size = digits.size() - 1;
        for (int i = 0; i < size; i++) {
            int value = digits.get(i);

            if (value > 9) {
                digits.set(i, value - 9);
            }
        }
        return digits;
    }

    private int sumDigits(List<Integer> digits) {
        return digits.stream()
                .reduce(0, Integer::sum);
    }
}
