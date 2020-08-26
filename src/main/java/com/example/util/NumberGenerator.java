package com.example.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator implements Generator {

    private final String IIN = "400000";

    private Random random;

    public NumberGenerator(Random random) {
        this.random = random;
    }

    @Override
    public String getCardNumber() {
        return IIN + IntStream.range(0, 10)
                .mapToObj(e -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }

    @Override
    public String getPIN() {
        return IntStream.range(0, 4)
                .mapToObj(e -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }
}
