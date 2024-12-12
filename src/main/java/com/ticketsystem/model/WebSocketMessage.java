package com.ticketsystem.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class WebSocketMessage {
    private String type;
    private String content;
    private long timestamp;
}
