package com.example.service;

import java.util.Scanner;

public class Bank {

    private Scanner scanner;

    public Bank(Scanner scanner) {
        this.scanner = scanner;
    }

    public void runSystem() {
        int option;

        do {
            mainMenu();
            option = getUserInput();
            switch (option) {
                case 1:
                    System.out.println("option 1");
                    //createAccount();
                    break;
                case 2:
                    System.out.println("option 2");
                    //logIntoAccount();
                    break;
                case 0:
                    break;
                default:
                    displayAlert();
            }
        } while (option != 0);

        System.out.println("Bye!");
    }

    private static void mainMenu() {
        System.out.println("\n1. Create an account");
        System.out.println("2. Log into account");
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
}
