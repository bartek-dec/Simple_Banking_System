package com.example.domain;

import java.util.List;

public class CardServiceImpl implements CardService {

    private CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card findCard(String cardNumber) {
        if (cardNumber == null) {
            return null;
        }

        return repository.findCard(cardNumber);
    }

    @Override
    public List<String> findAllCardNumbers() {
        return repository.findAllCardNumbers();
    }

    @Override
    public int addCard(Card card) {
        return repository.addCard(card);
    }

    @Override
    public int deleteAccount(String cardNumber) {
        return repository.deleteAccount(cardNumber);
    }
}
