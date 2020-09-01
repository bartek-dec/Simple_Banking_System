package com.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardRepositoryImplTest {

    private final String url = "jdbc:sqlite:./src/test/resources/cards.db";
    private SQLiteDataSource dataSource;
    private CardRepository repository;

    @BeforeEach
    void setUp() {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        repository = new CardRepositoryImpl(dataSource);

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM card");

                statement.executeUpdate("INSERT INTO card ('number', 'pin') VALUES ('4000008449433403', '1234')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findCard() {
        Card expectedCard = new CreditCard("4000008449433403", "1234");

        assertEquals(expectedCard, repository.findCard("4000008449433403"));
    }

    @Test
    void findCardWhenWrongNumber() {
        assertNull(repository.findCard("4000008449433400"));
    }

    @Test
    void findAllCardNumbers() {
        List<String> expectedNumbers = new ArrayList<>();
        expectedNumbers.add("4000008449433403");

        assertEquals(expectedNumbers, repository.findAllCardNumbers());
    }

    @Test
    void addCard() {
        Card cardToAdd = new CreditCard("4000008449433404", "4444");

        assertEquals(1, repository.addCard(cardToAdd));
    }
}