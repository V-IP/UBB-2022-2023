package com.example.lab_map_javafx.repository;

import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;

    public UserDatabaseRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM users"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users (last_name, first_name, username, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long aLong) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findOneById(Long aLong) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");) {

            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");

                user = new User(lastName, firstName, userName, password);
                user.setId(aLong);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Iterable<User> findOneByName(String lastName, String firstName) {
//        Set<User> users = new HashSet<>();
//        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE last_name=? AND first_name=?");) {
//
//            statement.setString(1, lastName);
//            statement.setString(2, firstName);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String userName = resultSet.getString("username");
//                String password = resultSet.getString("password");
//
//                User user = new User(lastName, firstName, userName, password);
//                user.setId(id);
//                users.add(user);
//            } return users;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Iterable<User> findOneByUsername(String userName) {
//        Set<User> users = new HashSet<>();
//        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?");) {
//
//            statement.setString(1, userName);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String lastName = resultSet.getString("last_name");
//                String firstName = resultSet.getString("first_name");
//                String password = resultSet.getString("password");
//
//                User user = new User(lastName, firstName, userName, password);
//                user.setId(id);
//                users.add(user);
//            }
//            return users;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void update(User entity, User newEntity) {
        String sql = "UPDATE users SET last_name=?, first_name=?, username=?, password=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newEntity.getLastName());
            statement.setString(2, newEntity.getFirstName());
            statement.setString(3, newEntity.getUsername());
            statement.setString(4, newEntity.getPassword());
            statement.setLong(5, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(lastName, firstName, userName, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
