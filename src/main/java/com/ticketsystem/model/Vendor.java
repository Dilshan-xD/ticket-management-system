package com.ticketsystem.model;

import com.ticketsystem.service.TicketService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Vendor implements Runnable {
    private final String vendorId;
    private final int releaseRate;
    private final TicketPool ticketPool;

    @Setter
    private TicketService ticketService;

    public Vendor(String vendorId, int releaseRate) {
        this.vendorId = vendorId;
        this.releaseRate = releaseRate;
        this.ticketPool = TicketPool.getInstance();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ticket ticket = new Ticket();
                ticket.setVendorId(vendorId);
                ticket.setStatus("AVAILABLE");

                ticketPool.addTickets(ticket);
                ticketService.notifyTicketOperation("Ticket Released", vendorId);
                log.info("Vendor {} released a ticket. Available tickets: {}",
                        vendorId, ticketPool.getAvailableTickets());

                Thread.sleep(releaseRate);
            }
        } catch (InterruptedException e) {
            log.info("Vendor {} stopped", vendorId);
            Thread.currentThread().interrupt();
        }
    }
}
