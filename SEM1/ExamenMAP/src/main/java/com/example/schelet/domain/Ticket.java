package com.example.schelet.domain;

import java.time.LocalDateTime;

public class Ticket extends Entity<Long>{
    String username;
    LocalDateTime purchaseTime;

    public Ticket(String username, LocalDateTime purchaseTime) {
        this.username = username;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }
}