package com.example.lab_map_javafx.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Struct<Long, Long, LocalDateTime>> {

    private String info;

    public Message(long user1, long user2, LocalDateTime date, String info) {
        setId(new Struct<>(user1,user2, date));
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
