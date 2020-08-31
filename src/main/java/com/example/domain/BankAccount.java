package com.example.domain;

public class BankAccount implements Account {

    private Card card;
    private int balance;

    public BankAccount(Card card) {
        this.card = card;
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
