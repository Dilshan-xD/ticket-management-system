import React from 'react';
import { Box, Typography, CircularProgress } from '@mui/material';

export default function TicketStatus({ status }) {
    return (
        <Box sx={{ p: 3, bgcolor: 'background.paper', borderRadius: 1 }}>
            <Typography variant="h5" gutterBottom>Ticket Status</Typography>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                <CircularProgress variant="determinate" value={status.availableTickets} />
                <Typography>
                    Available Tickets: {status.availableTickets}
                </Typography>
            </Box>
        </Box>
    );
}
