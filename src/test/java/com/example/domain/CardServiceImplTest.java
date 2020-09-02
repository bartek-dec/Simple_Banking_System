package com.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CardServiceImplTest {

    private CardService service;
    @Mock
    private CardRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CardRepositoryImpl.class);
        service = new CardServiceImpl(repository);
    }

    @Test
    void findCardWhenCardNumberNull() {
        assertNull(service.findCard(null));
    }

    @Test
    void findCard() {
        Card expected = new CreditCard("4000008449433403", "1234");
        when(repository.findCard(anyString())).thenReturn(expected);

        Card card = service.findCard("4000008449433403");

        assertEquals(expected, card);
    }

    @Test
    void findAllCardNumbers() {
        List<String> expectedNumbers = new ArrayList<>();
        expectedNumbers.add("4000008449433403");
        expectedNumbers.add("4000008449433401");

        when(repository.findAllCardNumbers()).thenReturn(expectedNumbers);

        List<String> foundNumbers = service.findAllCardNumbers();

        assertEquals(expectedNumbers, foundNumbers);
    }

    @Test
    void addCard() {
        Card cardToAdd = new CreditCard("4000008449433403", "1234");

        when(repository.addCard(any())).thenReturn(1);

        int returnedValue = service.addCard(cardToAdd);

        assertEquals(1, returnedValue);
    }

    @Test
    void deleteAccount() {
        String number = "4000002141215291";

        when(repository.deleteAccount(anyString())).thenReturn(1);

        int returnedValue = repository.deleteAccount(number);

        assertEquals(1, returnedValue);
    }
}