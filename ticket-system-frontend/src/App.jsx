import React, { useState, useEffect } from 'react';
import { Container, Grid, Typography, Box } from '@mui/material';
import ControlPanel from './components/ControlPanel';
import TicketStatus from './components/TicketStatus';
import LogViewer from './components/LogViewer';
import { ticketService } from './services/api';

function App() {
    const [ticketStatus, setTicketStatus] = useState({
        availableTickets: 0
    });
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/ws');

        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            if (data.type === 'status') {
                setTicketStatus(data);
            } else {
                setLogs(prev => [...prev, data]);
            }
        };

        return () => {
            ws.close();
        };
    }, []);

    return (
        <Box sx={{ bgcolor: '#f5f5f5', minHeight: '100vh', py: 4 }}>
            <Container>
                <Typography variant="h3" gutterBottom align="center">
                    Ticket Management System
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <ControlPanel />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <TicketStatus status={ticketStatus} />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <LogViewer logs={logs} />
                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
}

export default App;
