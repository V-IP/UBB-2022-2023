package com.example.schelet.controller;

import com.example.schelet.domain.Flight;

import java.util.ArrayList;

public class Observable {
    ArrayList<Observer> observers;

    public Observable(){
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void updateAll(Flight flight){
        observers.forEach(observer -> {observer.update(flight);});
    }
}
