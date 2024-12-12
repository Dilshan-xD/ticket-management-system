package com.ticketsystem.controller;

import com.ticketsystem.model.TicketPool;
import com.ticketsystem.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/vendor/start")
    public ResponseEntity<?> startVendor(@RequestParam String vendorId,
                                         @RequestParam int releaseRate) {
        ticketService.startVendor(vendorId, releaseRate);
        return ResponseEntity.ok("Vendor started successfully");
    }

    @PostMapping("/customer/start")
    public ResponseEntity<?> startCustomer(@RequestParam String customerId,
                                           @RequestParam int purchaseRate) {
        ticketService.startCustomer(customerId, purchaseRate);
        return ResponseEntity.ok("Customer started successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getTicketStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("availableTickets", TicketPool.getInstance().getAvailableTickets());
        return ResponseEntity.ok(status);
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stopAll() {
        ticketService.stopAll();
        return ResponseEntity.ok("All operations stopped");
    }
}