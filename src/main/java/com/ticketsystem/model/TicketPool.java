package com.ticketsystem.model;

import lombok.Getter;

import java.util.Vector;

@Getter
public class TicketPool {
    private final Vector<Ticket> tickets = new Vector<>();
    private static TicketPool instance;

    private TicketPool() {}

    public static synchronized TicketPool getInstance() {
        if (instance == null) {
            instance = new TicketPool();
        }
        return instance;
    }

    public synchronized void addTickets(Ticket ticket) {
        tickets.add(ticket);
    }

    public synchronized Ticket removeTicket() {
        if (!tickets.isEmpty()) {
            return tickets.remove(0);
        }
        return null;
    }

    public synchronized int getAvailableTickets() {
        return tickets.size();
    }
}

