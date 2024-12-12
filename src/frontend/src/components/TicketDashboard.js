import React, { useEffect, useState } from 'react';
import { connectWebSocket } from '../services/websocket';

const TicketDashboard = () => {
    const [ticketStatus, setTicketStatus] = useState({ availableTickets: 0 });
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        // Fetch initial status
        fetch('http://localhost:8080/api/tickets/status')
            .then(response => response.json())
            .then(data => setTicketStatus(data));

        // Connect WebSocket
        connectWebSocket((message) => {
            setTicketStatus(prevStatus => ({
                ...prevStatus,
                availableTickets: message.availableTickets
            }));

            setLogs(prevLogs => [...prevLogs, {
                operation: message.operation,
                id: message.id,
                timestamp: message.timestamp
            }]);
        });
    }, []);

    return (
        <div>
            <h2>Ticket Status</h2>
            <p>Available Tickets: {ticketStatus.availableTickets}</p>

            <h2>System Logs</h2>
            <div className="logs">
                {logs.map((log, index) => (
                    <div key={index}>
                        {log.timestamp} - {log.operation} by {log.id}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default TicketDashboard;

<button onClick={handleStopOperations}>Stop All Operations</button>

const handleStopOperations = () => {
    fetch('http://localhost:8080/api/tickets/stop', {
        method: 'POST',
    })
    .then(response => {
        if (response.ok) {
            console.log('Operations stopped successfully');
        }
    });
};

