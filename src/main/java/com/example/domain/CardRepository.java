package com.example.domain;

import java.util.List;

public interface CardRepository {

    Card findCard(String cardNumber);

    List<String> findAllCardNumbers();

    int addCard(Card card);
}
