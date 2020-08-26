package com.example.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator implements Generator {

    private final Set<String> cardNumbers;
    private final String IIN = "400000";

    private Random random;

    public NumberGenerator(Random random) {
        this.random = random;
        cardNumbers = new HashSet<>();
    }

    @Override
    public String getCardNumber() {
        String cardNumber;
        do {
            cardNumber = IIN + IntStream.range(0, 10)
                    .mapToObj(e -> String.valueOf(random.nextInt(10)))
                    .collect(Collectors.joining(""));
        } while (!cardNumbers.add(cardNumber));

        return cardNumber;
    }

    @Override
    public String getPIN() {
        return IntStream.range(0, 4)
                .mapToObj(e -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }
}
