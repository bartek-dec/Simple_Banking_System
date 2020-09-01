package com.example.util;

import com.example.domain.CardService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator implements Generator {

    private CardService service;
    private final String BIN = "400000";

    private Random random;

    public NumberGenerator(Random random, CardService service) {
        this.random = random;
        this.service = service;
    }

    @Override
    public String getCardNumber() {
        List<String> numbers = service.findAllCardNumbers();
        String cardNumber;
        do {
            cardNumber = BIN + generateAccountIdentifier();
            cardNumber = generateByLuhn(cardNumber);
        } while (numbers.contains(cardNumber));

        return cardNumber;
    }

    private String generateAccountIdentifier() {
        return IntStream.range(0, 9)
                .mapToObj(e -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }

    private String generateByLuhn(String number) {
        List<Integer> digits = Arrays.stream(number.split(""))
                .map(e -> Integer.parseInt(e))
                .collect(Collectors.toList());

        digits = multiplyOddDigits(digits);
        digits = reduceByNine(digits);
        int sum = sumDigits(digits);
        int lastDigit = findLastDigit(sum);

        return number + lastDigit;
    }

    private List<Integer> multiplyOddDigits(List<Integer> digits) {
        int size = digits.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                digits.set(i, digits.get(i) * 2);
            }
        }
        return digits;
    }

    private List<Integer> reduceByNine(List<Integer> digits) {
        int size = digits.size();
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

    private int findLastDigit(int sum) {
        if (sum % 10 == 0) {
            return 0;
        }

        sum++;
        int counter = 1;
        while (sum % 10 != 0) {
            sum++;
            counter++;
        }
        return counter;
    }

    @Override
    public String getPIN() {
        return IntStream.range(0, 4)
                .mapToObj(e -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }
}
