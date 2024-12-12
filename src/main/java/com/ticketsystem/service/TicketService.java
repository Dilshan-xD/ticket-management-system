package com.ticketsystem.service;

import com.ticketsystem.model.Customer;
import com.ticketsystem.model.TicketPool;
import com.ticketsystem.model.Vendor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TicketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final List<Thread> activeThreads = new ArrayList<>();
    private final TicketPool ticketPool = TicketPool.getInstance();

    public TicketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void startVendor(String vendorId, int releaseRate) {
        Vendor vendor = new Vendor(vendorId, releaseRate);
        vendor.setTicketService(this);
        Thread vendorThread = new Thread(vendor);
        activeThreads.add(vendorThread);
        executorService.submit(vendor);
        notifyTicketOperation("Vendor Started", vendorId);
        log.info("Vendor {} started with release rate {} tickets/sec", vendorId, 1000.0/releaseRate);
    }

    public void startCustomer(String customerId, int purchaseRate) {
        Customer customer = new Customer(customerId, purchaseRate);
        customer.setTicketService(this);
        Thread customerThread = new Thread(customer);
        activeThreads.add(customerThread);
        executorService.submit(customer);
        notifyTicketOperation("Customer Started", customerId);
        log.info("Customer {} started with purchase rate {} tickets/sec", customerId, 1000.0/purchaseRate);
    }

    public void stopAll() {
        // Interrupt all active threads
        activeThreads.forEach(Thread::interrupt);

        // Shutdown executor immediately
        executorService.shutdownNow();

        // Send final notification
        notifyTicketOperation("System", "All operations stopped");
        log.info("All operations stopped");

        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void updateTicketStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("availableTickets", ticketPool.getAvailableTickets());
        messagingTemplate.convertAndSend("/topic/status", status);
    }

    public void notifyTicketOperation(String operation, String id) {
        Map<String, Object> message = new HashMap<>();
        message.put("operation", operation);
        message.put("id", id);
        message.put("timestamp", LocalDateTime.now());
        message.put("availableTickets", ticketPool.getAvailableTickets());

        messagingTemplate.convertAndSend("/topic/tickets", message);
    }
}