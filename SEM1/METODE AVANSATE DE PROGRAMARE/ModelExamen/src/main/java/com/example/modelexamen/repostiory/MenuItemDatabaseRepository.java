package com.example.modelexamen.repostiory;


import com.example.modelexamen.domain.MenuItem;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MenuItemDatabaseRepository {
    private String url;
    private String username;
    private String password;

    public MenuItemDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM dMenuItem"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(MenuItem entity) {
        String sql = "INSERT INTO dMenuItem(Id, Category, Item, Price, Currency) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getCategory());
            statement.setString(3, entity.getItem());
            statement.setFloat(4, entity.getPrice());
            statement.setString(5, entity.getCurrency());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM dMenuItem WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MenuItem findOneById(int id) {
        MenuItem object = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dMenuItem WHERE id=?");) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String Category = resultSet.getString("Category");
                String Item = resultSet.getString("Item");
                Float Price = resultSet.getFloat("Price");
                String Currency = resultSet.getString("Currency");
                object = new MenuItem(Category, Item, Price, Currency);
                object.setId(id);
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<MenuItem> findAll() {
        Set<MenuItem> list = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dMenuItem"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int Id = resultSet.getInt("Id");
                String Category = resultSet.getString("Category");
                String Item = resultSet.getString("Item");
                Float Price = resultSet.getFloat("Price");
                String Currency = resultSet.getString("Currency");

                MenuItem object = new MenuItem(Category, Item, Price, Currency);
                object.setId(Id);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}