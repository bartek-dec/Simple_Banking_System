package com.example.service;

import com.example.domain.Account;
import com.example.domain.BankAccount;
import com.example.domain.Card;
import com.example.domain.CreditCard;
import com.example.util.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Bank {

    private List<Account> accounts;
    private Scanner scanner;
    private Generator generator;

    public Bank(Scanner scanner, Generator generator) {
        this.scanner = scanner;
        this.generator = generator;
        this.accounts = new ArrayList<>();
    }

    public void runSystem() {
        int option;
        Account account;
        do {
            mainMenu();
            option = getUserInput();
            switch (option) {
                case 1:
                    account = createAccount();
                    accounts.add(account);
                    System.out.println(displayAccount(account));
                    break;
                case 2:
                    account = logIntoAccount();

                    if (account != null) {
                        System.out.println("\nYou have successfully logged in!\n");

                        do {
                            displayLogInSubmenu();
                            option = getUserInput();

                            switch (option) {
                                case 1:
                                    showBalance(account);
                                    break;
                                case 2:
                                    System.out.println("\nYou have successfully logged out!\n");
                                    break;
                                case 0:
                                    break;
                            }
                        } while (option == 1);
                    }
                    break;
                case 0:
                    break;
                default:
                    displayAlert();
            }
        } while (option != 0);

        System.out.println("\nBye!");
    }

    private void mainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    private void displayLogInSubmenu() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }

    private void displayAlert() {
        System.out.println("Incorrect input!!!");
    }

    private int getUserInput() {
        int input;

        try {
            input = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }

        return input;
    }

    private Account createAccount() {
        Card card = createCard();
        return new BankAccount(card);
    }

    private Card createCard() {
        String cardNumber = generator.getCardNumber();
        String pin = generator.getPIN();

        return new CreditCard(cardNumber, pin);
    }

    private String displayAccount(Account account) {
        StringBuilder builder = new StringBuilder();

        builder.append("\nYour card has been created\n");
        builder.append("Your card number:\n");
        builder.append(account.getCard().getCardNumber() + "\n");
        builder.append("Your card PIN:\n");
        builder.append(account.getCard().getPIN() + "\n");

        return builder.toString();
    }

    private Account logIntoAccount() {
        System.out.println("\nEnter your card number:");
        String cardNumber = scanner.nextLine().trim();

        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine().trim();

        Account account = findAccount(cardNumber);

        if (account == null) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        } else if (Objects.equals(account.getCard().getPIN(), pin)) {
            return account;
        }

        return null;
    }

    private Account findAccount(String cardNumber) {
        if (cardNumber == null) {
            return null;
        }

        return accounts.stream()
                .filter(e -> Objects.equals(e.getCard().getCardNumber(), cardNumber))
                .findFirst().orElse(null);
    }

    private void showBalance(Account account) {
        System.out.println("\nBalance: " + account.getBalance() + "\n");
    }
}
