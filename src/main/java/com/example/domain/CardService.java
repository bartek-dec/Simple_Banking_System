package com.example.domain;

import java.util.List;

public interface CardService {

    Card findCard(String cardNumber);

    List<String> findAllCardNumbers();

    int addCard(Card card);

    int deleteAccount(String cardNumber);

    int addBalance(int amount, String cardNumber);

    int transferMoney(int transfer, String sender, String receiver);
}
