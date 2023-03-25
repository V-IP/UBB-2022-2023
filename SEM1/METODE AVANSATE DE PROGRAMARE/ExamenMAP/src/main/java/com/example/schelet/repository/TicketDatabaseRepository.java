package com.example.schelet.repository;

import com.example.schelet.domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TicketDatabaseRepository {
    private String url;
    private String username;
    private String password;

    public TicketDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM dTicket"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Ticket entity) {
        String sql = "INSERT INTO dTicket(Id, CUsername, PurchaseTime) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPurchaseTime().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ticket findOneById(Long id) {
        Ticket object = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dTicket WHERE id=?");) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String Username = resultSet.getString("CUsername");
                LocalDateTime purchaseTime = LocalDateTime.parse(resultSet.getString("PurchaseTime"));
                object = new Ticket(Username, purchaseTime);
                object.setId(id);
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Ticket> findAll() {
        Set<Ticket> list = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dTicket"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long Id = resultSet.getLong("Id");
                String Username = resultSet.getString("CUsername");
                LocalDateTime purchaseTime = LocalDateTime.parse(resultSet.getString("PurchaseTime"));

                Ticket object = new Ticket(Username, purchaseTime);
                object.setId(Id);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}