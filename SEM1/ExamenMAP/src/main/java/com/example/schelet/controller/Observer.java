package com.example.schelet.controller;

import com.example.schelet.domain.Flight;

public abstract class Observer {
    public abstract void update(Flight flight);
}
