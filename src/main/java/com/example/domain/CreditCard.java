package com.example.domain;

public class CreditCard implements Card {

    private String cardNumber;
    private String PIN;
    private int balance;

    public CreditCard(String cardNumber, String PIN) {
        this.cardNumber = cardNumber;
        this.PIN = PIN;
    }

    @Override
    public void setCardNumber(String number) {
        this.cardNumber = number;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public void setPIN(String pin) {
        this.PIN = pin;
    }

    @Override
    public String getPIN() {
        return PIN;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
