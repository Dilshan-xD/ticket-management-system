package com.ticketsystem.model;

import com.ticketsystem.service.TicketService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Customer implements Runnable {
    private final String customerId;
    private final int purchaseRate;
    private final TicketPool ticketPool;

    @Setter
    private TicketService ticketService;

    public Customer(String customerId, int purchaseRate) {
        this.customerId = customerId;
        this.purchaseRate = purchaseRate;
        this.ticketPool = TicketPool.getInstance();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ticket ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    ticket.setCustomerId(customerId);
                    ticket.setStatus("SOLD");
                    ticketService.notifyTicketOperation("Ticket Purchased", customerId);
                    log.info("Customer {} purchased a ticket. Remaining tickets: {}",
                            customerId, ticketPool.getAvailableTickets());
                }

                Thread.sleep(purchaseRate);
            }
        } catch (InterruptedException e) {
            log.info("Customer {} stopped", customerId);
            Thread.currentThread().interrupt();
        }
    }
}
