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

                        boolean flag = true;
                        do {
                            displayLogInSubmenu();
                            option = getUserInput();

                            switch (option) {
                                case 1:
                                    showBalance(card);
                                    break;
                                case 2:
                                    addBalance(card.getCardNumber());
                                    break;
                                case 3:
                                    doTransfer(card);
                                    break;
                                case 4:
                                    closeAccount(card.getCardNumber());
                                    break;
                                case 5:
                                    System.out.println("\nYou have successfully logged out!\n");
                                    flag = false;
                                    break;
                                case 0:
                                    flag = false;
                                    break;
                            }
                        } while (flag);
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
        String cardNumber = readCardNumber();
        String pin = readPIN();

        boolean isValid = validator.validate(cardNumber);

        if (!isValid) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        }

        Card account = findCard(cardNumber);

        if (account == null) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        } else if (!Objects.equals(account.getPIN(), pin)) {
            System.out.println("\nWrong card number or PIN!\n");
            return null;
        }

        return account;
    }

    private String readPIN() {
        System.out.println("Enter your PIN:");
        return scanner.nextLine().trim();
    }

    private String readCardNumber() {
        System.out.println("\nEnter your card number:");
        return scanner.nextLine().trim();
    }

    private Card findCard(String cardNumber) {
        return service.findCard(cardNumber);
    }

    private void showBalance(Card card) {
        Card updatedCard = service.findCard(card.getCardNumber());
        System.out.println("\nBalance: " + updatedCard.getBalance() + "\n");
    }


    private void closeAccount(String cardNumber) {
        service.deleteAccount(cardNumber);
        System.out.println("\nThe account has been closed!\n");
    }

    private void addBalance(String cardNumber) {
        System.out.println("\nEnter income:");
        int income = readIncome();

        if (income < 0) {
            System.out.println("Incorrect income!!!\n");
        } else {
            service.addBalance(income, cardNumber);
            System.out.println("Income was added!\n");
        }
    }

    private int readIncome() {
        int income;
        try {
            income = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
        return income;
    }

    private void doTransfer(Card sender) {
        System.out.println("\nTransfer");
        System.out.println("Enter card number:");

        String receiverNumber = scanner.nextLine().trim();
        boolean isValid = validator.validate(receiverNumber);
        if (!isValid) {
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            return;
        }

        Card receiver = service.findCard(receiverNumber);
        if (receiver == null) {
            System.out.println("Such a card does not exist.\n");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int transfer = readIncome();
        if (transfer < 0) {
            System.out.println("Incorrect income!!!\n");
            return;
        } else if (transfer > sender.getBalance()) {
            System.out.println("Not enough money!\n");
            return;
        }

        service.transferMoney(transfer, sender.getCardNumber(), receiver.getCardNumber());
        System.out.println("Success!\n");
    }
}
