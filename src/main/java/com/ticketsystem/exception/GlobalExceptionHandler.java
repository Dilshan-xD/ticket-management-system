package com.ticketsystem.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketException.class)
    public ResponseEntity<?> handleTicketException(TicketException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
