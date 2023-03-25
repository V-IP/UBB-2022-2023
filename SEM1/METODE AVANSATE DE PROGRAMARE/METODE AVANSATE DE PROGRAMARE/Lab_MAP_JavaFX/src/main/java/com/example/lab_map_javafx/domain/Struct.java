package com.example.lab_map_javafx.domain;

public class Struct<E1, E2, Date> {
    private E1 from;
    private E2 to;
    private Date date;

    public Struct(E1 from, E2 to, Date date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public E1 getFrom() {
        return from;
    }

    public E2 getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }
}