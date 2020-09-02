package com.example.domain;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private String findCard = "SELECT * FROM card WHERE number = '";
    private String findAllCardNumbers = "SELECT number FROM card";
    private String addCard = "INSERT INTO card ('number', 'pin') VALUES ('";
    private String deleteAccount = "DELETE FROM card WHERE number = '";
    private String addBalance = "UPDATE card SET balance = balance + ";

    private SQLiteDataSource dataSource;

    public CardRepositoryImpl(SQLiteDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Card findCard(String cardNumber) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(findCard + cardNumber + "'")) {
                    if (resultSet.next()) {
                        String number = resultSet.getString("number");
                        String pin = resultSet.getString("pin");
                        int balance = resultSet.getInt("balance");

                        Card card = new CreditCard(number, pin);
                        card.setBalance(balance);

                        return card;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> findAllCardNumbers() {
        List<String> numbers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(findAllCardNumbers)) {
                    while (resultSet.next()) {
                        numbers.add(resultSet.getString("number"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    @Override
    public int addCard(Card card) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String query = addCard + card.getCardNumber() + "', '" +
                        card.getPIN() + "')";
                return statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteAccount(String cardNumber) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String query = deleteAccount + cardNumber + "'";
                return statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addBalance(int amount, String cardNumber) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String query = addBalance + amount + " WHERE number = '" + cardNumber + "'";
                return statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
