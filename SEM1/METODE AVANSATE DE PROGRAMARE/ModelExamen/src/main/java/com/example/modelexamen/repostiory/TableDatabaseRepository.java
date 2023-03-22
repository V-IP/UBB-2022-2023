package com.example.modelexamen.repostiory;

import com.example.modelexamen.domain.Table;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TableDatabaseRepository {
    private String url;
    private String username;
    private String password;

    public TableDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM dTable"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Table entity) {
        String sql = "INSERT INTO dTable(Id) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM dTable WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Table findOneById(int id) {
        Table object = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dTable WHERE id=?");) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                object = new Table();
                object.setId(id);
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Table> findAll() {
        Set<Table> list = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dTable"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int Id = resultSet.getInt("Id");

                Table object = new Table();
                object.setId(Id);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}