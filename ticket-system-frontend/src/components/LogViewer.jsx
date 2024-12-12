import React from 'react';
import { Box, Typography, Paper, List, ListItem, ListItemText } from '@mui/material';

export default function LogViewer({ logs }) {
    return (
        <Box sx={{ p: 3, bgcolor: 'background.paper', borderRadius: 1 }}>
            <Typography variant="h5" gutterBottom>System Logs</Typography>
            <Paper sx={{ maxHeight: 400, overflow: 'auto' }}>
                <List>
                    {logs.map((log, index) => (
                        <ListItem key={index}>
                            <ListItemText
                                primary={log.content}
                                secondary={new Date(log.timestamp).toLocaleString()}
                            />
                        </ListItem>
                    ))}
                </List>
            </Paper>
        </Box>
    );
}
