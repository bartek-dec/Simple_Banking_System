package com.example.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankAccount implements Account {

    private Card card;
    private BigDecimal balance;

    public BankAccount(Card card) {
        this.card = card;
        this.balance = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }
}
