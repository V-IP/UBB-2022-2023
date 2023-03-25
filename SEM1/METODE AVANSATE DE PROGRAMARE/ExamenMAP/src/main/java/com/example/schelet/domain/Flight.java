package com.example.schelet.domain;

import java.time.LocalDateTime;

public class Flight extends Entity<Long>{
    String from;
    String to;
    LocalDateTime departureTime;
    LocalDateTime landingTime;
    int seats;
    public Flight(String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, int seats) {
        this.from=from;
        this.to=to;
        this.departureTime=departureTime;
        this.landingTime=landingTime;
        this.seats=seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public int getSeats() {
        return seats;
    }
}
