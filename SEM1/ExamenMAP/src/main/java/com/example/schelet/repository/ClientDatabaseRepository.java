package com.example.schelet.repository;

import com.example.schelet.domain.Client;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDatabaseRepository {
    private String url;
    private String username;
    private String password;

    public ClientDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM dClient"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client findOneById(String cusername) {
        Client client = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dClient WHERE CUsername LIKE ?");) {

            statement.setString(1, cusername);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String Name = resultSet.getString("CName");
                client = new Client(cusername,Name);
            }
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Client> findAll() {
        Set<Client> list = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dClient"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String Username = resultSet.getString("CUsername");
                String Name = resultSet.getString("CName");

                Client Client = new Client(Username,Name);
                list.add(Client);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}