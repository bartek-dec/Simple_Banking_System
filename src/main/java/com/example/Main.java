package com.example;

import com.example.domain.CardRepository;
import com.example.domain.CardRepositoryImpl;
import com.example.domain.CardService;
import com.example.domain.CardServiceImpl;
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

    private static final String url = "jdbc:sqlite:src/main/resources/";
    private static final String path = "./src/main/resources/";
    private static final String query = "CREATE TABLE IF NOT EXISTS card(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "number TEXT, " +
            "pin TEXT, " +
            "balance INTEGER DEFAULT 0);";

    public static void main(String[] args) {
        //program arguments->  -fileName cards.db
        Optional<String> nameOptional = Arrays.stream(args)
                .filter(e -> e.contains(".db"))
                .map(String::trim)
                .findFirst();

        if (nameOptional.isEmpty()) {
            return;
        }

        String dbName = nameOptional.get();
        File dataBase = new File(path + dbName);

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url + dbName);

        if (!dataBase.exists()) {
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
        CardRepository repository = new CardRepositoryImpl(dataSource);
        CardService service = new CardServiceImpl(repository);
        Generator generator = new NumberGenerator(random, service);
        Validator validator = new CardNumberValidator();
        Bank bank = new Bank(scanner, generator, validator, service);

        bank.runSystem();
    }
}
