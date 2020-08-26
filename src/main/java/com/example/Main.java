package com.example;

import com.example.service.Bank;
import com.example.util.Generator;
import com.example.util.NumberGenerator;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Generator generator = new NumberGenerator(random);
        Bank bank = new Bank(scanner,generator);

        bank.runSystem();
    }
}
