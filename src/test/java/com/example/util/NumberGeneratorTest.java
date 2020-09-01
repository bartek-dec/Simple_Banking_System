package com.example.util;

import com.example.domain.CardService;
import com.example.domain.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {

    private final String cardNumberMatcher = "400000[0-9]{10}";
    private final String pinMatcher = "[0-9]{4}";

    private Random random;
    @Mock
    private CardService service;
    private Generator generator;

    @BeforeEach
    void setUp() {
        random = new Random();
        service = Mockito.mock(CardServiceImpl.class);
        generator = new NumberGenerator(random, service);
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