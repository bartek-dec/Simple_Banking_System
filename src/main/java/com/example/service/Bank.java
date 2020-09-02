package com.example.service;

import com.example.domain.Card;
import com.example.domain.CardService;
import com.example.domain.CreditCard;
import com.example.util.Generator;
import com.example.util.Validator;

import java.util.Objects;
import java.util.Scanner;

public class Bank {

    private Scanner scanner;
    private Generator generator;
    private Validator validator;
    private CardService service;

    public Bank(Scanner scanner, Generator generator, Validator validator, CardService service) {
        this.scanner = scanner;
        this.generator = generator;
        this.validator = validator;
        this.service = service;
    }

    public void runSystem() {
        int option;
        Card card;
        do {
            mainMenu();
            option = getUserInput();
            switch (option) {
                case 1:
                    card = createCard();
                    service.addCard(card);
                    System.out.println(displayCard(card));
                    break;
                case 2:
                    card = logIntoAccount();

                    if (card != null) {
                        System.out.println("\nYou have successfully logged in!\n");

                        do {
                            displayLogInSubmenu();
                            option = getUserInput();

                            switch (option) {
                                case 1:
                                    showBalance(card);
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    closeAccount(card.getCardNumber());
                                    System.out.println("\nThe account has been closed!\n");
                                    break;
                                case 5:
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
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
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

    private Card createCard() {
        String cardNumber = generator.getCardNumber();
        String pin = generator.getPIN();

        return new CreditCard(cardNumber, pin);
    }

    private String displayCard(Card card) {
        StringBuilder builder = new StringBuilder();

        builder.append("\nYour card has been created\n");
        builder.append("Your card number:\n");
        builder.append(card.getCardNumber() + "\n");
        builder.append("Your card PIN:\n");
        builder.append(card.getPIN() + "\n");

        return builder.toString();
    }

    private Card logIntoAccount() {
        System.out.println("\nEnter your card number:");
        String cardNumber = scanner.nextLine().trim();

        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine().trim();

        boolean isValid = validator.validate(cardNumber);

        if (!isValid) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        }

        Card account = findCard(cardNumber);

        if (account == null) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        } else if (Objects.equals(account.getPIN(), pin)) {
            return account;
        }

        return null;
    }

    private Card findCard(String cardNumber) {
        return service.findCard(cardNumber);
    }

    private void showBalance(Card card) {
        System.out.println("\nBalance: " + card.getBalance() + "\n");
    }

    private void closeAccount(String cardNumber) {
        service.deleteAccount(cardNumber);
    }
}
