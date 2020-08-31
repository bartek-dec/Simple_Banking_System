package com.example;

import com.example.service.Bank;
import com.example.util.CardNumberValidator;
import com.example.util.Generator;
import com.example.util.NumberGenerator;
import com.example.util.Validator;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String path = "jdbc:sqlite:src/main/resources/";
    private static final String query = "CREATE TABLE IF NOT EXISTS card(" +
            "id INTEGER UNIQUE," +
            "number TEXT," +
            "pin TEXT," +
            "balance INTEGER DEFAULT 0);";

    public static void main(String[] args) {
        Optional<String> nameOptional = Arrays.stream(args)
                .filter(e -> e.contains(".db"))
                .findFirst();

        if (nameOptional.isEmpty()) {
            return;
        }

        String name = nameOptional.get();
        File dataBase = new File(path + name);
        SQLiteDataSource dataSource = new SQLiteDataSource();

        if (!dataBase.exists()) {
            dataSource.setUrl(path + name);
            try (Connection connection = dataSource.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                System.out.println("Failed to connect with database");
            }
        }
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Generator generator = new NumberGenerator(random);
        Validator validator = new CardNumberValidator();
        Bank bank = new Bank(scanner, generator, validator);

        bank.runSystem();
    }
}
