package com.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {

    private final String cardNumberMatcher = "400000[0-9]{10}";
    private final String pinMatcher = "[0-9]{4}";

    private Random random;
    private Generator generator;

    @BeforeEach
    void setUp() {
        random = new Random();
        generator = new NumberGenerator(random);
    }

    @Test
    void getCardNumber() {
        assertTrue(generator.getCardNumber().matches(cardNumberMatcher));
    }

    @Test
    void getPIN() {
        assertTrue(generator.getPIN().matches(pinMatcher));
    }
}