import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

export const connectWebSocket = (onStatusUpdate, onLogUpdate) => {
    const client = new Client({
        webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
        debug: (str) => {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

    client.onConnect = () => {
        // Subscribe to ticket status updates
        client.subscribe('/topic/status', (message) => {
            const status = JSON.parse(message.body);
            onStatusUpdate(status);
        });

        // Subscribe to system logs
        client.subscribe('/topic/tickets', (message) => {
            const log = JSON.parse(message.body);
            onLogUpdate(log);
        });
    };

    client.activate();
    return client;
};
