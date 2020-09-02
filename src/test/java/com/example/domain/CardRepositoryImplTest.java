package com.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
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
                statement.executeUpdate("INSERT INTO card ('number', 'pin') VALUES ('4000008449433402', '4444')");
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
        expectedNumbers.add("4000008449433402");

        assertEquals(expectedNumbers, repository.findAllCardNumbers());
    }

    @Test
    void addCard() {
        Card cardToAdd = new CreditCard("4000008449433404", "4444");

        assertEquals(1, repository.addCard(cardToAdd));
    }

    @Test
    void deleteAccountSuccess() {
        String number = "4000008449433403";

        assertEquals(1, repository.deleteAccount(number));
    }

    @Test
    void deleteAccountNoSuchNumber() {
        String number = "4000008449433400";

        assertEquals(0, repository.deleteAccount(number));
    }

    @Test
    void addBalance() {
        String number = "4000008449433403";

        int affectedRecords = repository.addBalance(100, number);

        int affectedBalance = -1;
        int notAffectedBalance = -1;
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String affectedRecord = "SELECT balance FROM card WHERE number = '4000008449433403'";
                try (ResultSet resultSet = statement.executeQuery(affectedRecord)) {
                    affectedBalance = resultSet.getInt("balance");
                }

                String notAffectedRecord = "SELECT balance FROM card WHERE number = '4000008449433402'";
                try (ResultSet resultSet = statement.executeQuery(notAffectedRecord)) {
                    notAffectedBalance = resultSet.getInt("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(1, affectedRecords);
        assertEquals(100, affectedBalance);
        assertEquals(0, notAffectedBalance);
    }
}