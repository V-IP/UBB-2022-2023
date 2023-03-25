package com.example.schelet.repository;

import com.example.schelet.domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FlightDatabaseRepository {
    private String url;
    private String username;
    private String password;

    public FlightDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM dFlight"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Flight entity) {
        String sql = "UPDATE dFlight SET seats=seats-1 WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Flight findOneById(Long id) {
        Flight object=null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dFlight WHERE id=?");) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String From = resultSet.getString("FFrom");
                String To = resultSet.getString("FTo");
                LocalDateTime departureTime = LocalDateTime.parse(resultSet.getString("DepartureTime"));
                LocalDateTime landingTime = LocalDateTime.parse(resultSet.getString("LandingTime"));
                int Seats = resultSet.getInt("Seats");
                object = new Flight(From, To, departureTime, landingTime, Seats);
                object.setId(id);
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterable<Flight> findAll() {
        Set<Flight> list = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM dFlight"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long Id=resultSet.getLong("Id");
                String From=resultSet.getString("FFrom");
                String To=resultSet.getString("FTo");
                LocalDateTime departureTime= LocalDateTime.parse(resultSet.getString("departureTime"));
                LocalDateTime landingTime= LocalDateTime.parse(resultSet.getString("landingTime"));
                int Seats=resultSet.getInt("Seats");

                Flight object = new Flight(From, To, departureTime, landingTime, Seats);
                object.setId(Id);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}