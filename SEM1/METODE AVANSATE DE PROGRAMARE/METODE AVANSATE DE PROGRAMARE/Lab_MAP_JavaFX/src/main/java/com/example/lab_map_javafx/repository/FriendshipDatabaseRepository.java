package com.example.lab_map_javafx.repository;

import com.example.lab_map_javafx.domain.Friendship;
import com.example.lab_map_javafx.domain.Tuple;
import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDatabaseRepository implements Repository<Tuple<Long, Long>, Friendship> {
    private String url;
    private String username;
    private String password;
    private Validator<Friendship> validator;

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id1) AS size FROM friendship"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Friendship entity) {
        String sql = "INSERT INTO friendships (id1, id2, friends_from, status, r_from) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId().getE1());
            statement.setLong(2, entity.getId().getE2());
            statement.setString(3, entity.getStringFriendsFrom());
            statement.setInt(4, entity.getStatus());
            statement.setLong(5, entity.getFrom());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Tuple<Long, Long> aLong) {
        String sql = "DELETE FROM friendships WHERE id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong.getE1());
            statement.setLong(2, aLong.getE2());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Friendship findOneById(Tuple<Long, Long> aLong) {
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE id1=? AND id2=?");) {

            statement.setLong(1, aLong.getE1());
            statement.setLong(2, aLong.getE2());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("friends_from");
                int status = resultSet.getInt("status");
                Long from = resultSet.getLong("r_from");

                friendship = new Friendship(id1, id2, friendsFrom, status, from);
            }
            return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Friendship entity, Friendship newEntity) {
        String sql = "UPDATE friendships SET friends_from=?, status=?, r_from=null WHERE id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newEntity.getStringFriendsFrom());
            statement.setLong(2, newEntity.getStatus());
            statement.setLong(3, entity.getId().getE1());
            statement.setLong(4, entity.getId().getE2());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("friends_from");
                int status = resultSet.getInt("status");
                Long from = resultSet.getLong("r_from");

                Friendship friendship = new Friendship(id1, id2, friendsFrom, status, from);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}