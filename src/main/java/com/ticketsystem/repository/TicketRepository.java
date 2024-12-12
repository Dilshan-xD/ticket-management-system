package com.ticketsystem.repository;

import com.ticketsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Custom queries if needed
}
