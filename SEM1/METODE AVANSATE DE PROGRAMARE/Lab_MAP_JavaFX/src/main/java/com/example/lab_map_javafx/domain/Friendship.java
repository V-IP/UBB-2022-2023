package com.example.lab_map_javafx.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Friendship extends Entity<Tuple<Long, Long>> {
    private String friendsFrom;
    private int status;
    private long from;


    public Friendship(long user1, long user2, long from) {
        setId(new Tuple<>(Math.min(user1, user2), Math.max(user1, user2)));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.friendsFrom = LocalDateTime.now().format(format);
        this.status = 0;
        this.from = from;
    }

    public Friendship(long user1, long user2, String friendsFrom, int status, long from) {
        setId(new Tuple<>(Math.min(user1, user2), Math.max(user1, user2)));
        this.friendsFrom = friendsFrom;
        this.status = status;
        this.from = from;
    }

    public String getStringFriendsFrom() {
        return friendsFrom;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }


}
