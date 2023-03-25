package com.example.lab_map_javafx.repository;

import com.example.lab_map_javafx.domain.Friendship;
import com.example.lab_map_javafx.domain.Message;
import com.example.lab_map_javafx.domain.Struct;
import com.example.lab_map_javafx.domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageDatabaseRepository implements Repository<Struct<Long, Long, LocalDateTime>, Message> {
    private String url;
    private String username;
    private String password;

    public MessageDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id1) AS size FROM messages"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO messages (id1, id2, m_date, info) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId().getFrom());
            statement.setLong(2, entity.getId().getTo());
            statement.setString(3, entity.getId().getDate().toString());
            statement.setString(4, entity.getInfo());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Struct<Long, Long, LocalDateTime> aLong) {
        String sql = "DELETE FROM messages WHERE id1=? AND id2=? AND m_date=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong.getFrom());
            statement.setLong(2, aLong.getTo());
            statement.setString(3, aLong.getDate().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message findOneById(Struct<Long, Long, LocalDateTime> aLong) {
        Message messages = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE id1=? AND id2=? AND m_date=?");) {

            statement.setLong(1, aLong.getFrom());
            statement.setLong(2, aLong.getTo());
            statement.setString(3, aLong.getDate().toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("m_date"));
                String info = resultSet.getString("info");

                messages = new Message(id1, id2, date, info);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Message entity, Message newEntity) {
        String sql = "UPDATE messages SET info=? WHERE id1=? AND id2=? AND m_date=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newEntity.getInfo());
            statement.setLong(2, entity.getId().getFrom());
            statement.setLong(3, entity.getId().getTo());
            statement.setString(4, entity.getId().getDate().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("m_date"));
                String info = resultSet.getString("info");

                Message message = new Message(id1, id2, date, info);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}