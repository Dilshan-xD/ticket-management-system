import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

let stompClient = null;

export const connectWebSocket = (onMessageReceived) => {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/tickets', (message) => {
            const data = JSON.parse(message.body);
            onMessageReceived(data);
        });

        stompClient.subscribe('/topic/logs', (message) => {
            const data = JSON.parse(message.body);
            onMessageReceived(data);
        });
    });
};
