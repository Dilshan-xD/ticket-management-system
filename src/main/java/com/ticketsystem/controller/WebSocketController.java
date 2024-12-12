package com.ticketsystem.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/ticketUpdates")
    @SendTo("/topic/tickets")
    public String handleTicketUpdates(String message) {
        return message;
    }
}
