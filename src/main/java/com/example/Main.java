package com.example;

import com.example.service.Bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank(scanner);

        bank.runSystem();
    }
}
