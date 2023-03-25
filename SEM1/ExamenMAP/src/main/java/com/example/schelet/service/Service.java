package com.example.schelet.service;

import com.example.schelet.domain.Client;
import com.example.schelet.domain.Flight;
import com.example.schelet.domain.Ticket;
import com.example.schelet.repository.ClientDatabaseRepository;
import com.example.schelet.repository.FlightDatabaseRepository;
import com.example.schelet.repository.TicketDatabaseRepository;

public class Service {

    private ClientDatabaseRepository client;
    private FlightDatabaseRepository flight;
    private TicketDatabaseRepository ticket;

    public Service() {
        this.client = new ClientDatabaseRepository("jdbc:postgresql://localhost:5432/ExamenMAP", "postgres", "postgres");
        this.flight = new FlightDatabaseRepository("jdbc:postgresql://localhost:5432/ExamenMAP", "postgres", "postgres");
        this.ticket = new TicketDatabaseRepository("jdbc:postgresql://localhost:5432/ExamenMAP", "postgres", "postgres");
    }

    public Client findClientByUsername(String username) {
        return client.findOneById(username);
    }

    public Flight getOneFlight(Long id) {
        return flight.findOneById(id);
    }

    public Iterable<Flight> getAllFlights() {
        return flight.findAll();
    }

    public void updateFlight(Flight f) {
        flight.update(f);
    }

    public void addOneTicket(Ticket o) {
        Long ct = Long.valueOf(0);
        for (Ticket t : ticket.findAll()) {
            if (t.getId() > ct) ct = t.getId();
        }
        o.setId(ct + 1);
        ticket.save(o);
    }
}
