package repository.database;

import domain.Friendship;
import domain.Tuple;
import repository.Repository;
import validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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
        String sql = "INSERT INTO friendships (id1, id2, friends_from) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getUser1());
            statement.setLong(2, entity.getUser2());
            statement.setString(3, entity.getStringFriendsFrom());
            //statement.setDate(3, entity.getDateFriendsFrom());

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

                friendship = new Friendship(id1, id2, friendsFrom);
            }
            return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Friendship entity, Friendship newEntity) {
        String sql = "UPDATE friendships SET id1=?, id2=?, friends_from=? WHERE id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, newEntity.getUser1());
            statement.setLong(2, newEntity.getUser2());
            statement.setString(3, newEntity.getStringFriendsFrom());
            statement.setLong(4, entity.getUser1());
            statement.setLong(5, entity.getUser2());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("friends_from");

                Friendship friendship = new Friendship(id1, id2, friendsFrom);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}