import React, { useState } from 'react';
import { Box, Button, TextField, Typography, Grid } from '@mui/material';
import { ticketService } from '../services/api';

export default function ControlPanel() {
    const [vendorId, setVendorId] = useState('');
    const [releaseRate, setReleaseRate] = useState(1);
    const [customerId, setCustomerId] = useState('');
    const [purchaseRate, setPurchaseRate] = useState(1);

    const handleStartVendor = async () => {
        try {
            await ticketService.startVendor(vendorId, Math.floor(1000 / releaseRate));
            setVendorId('');
            setReleaseRate(1);
        } catch (error) {
            console.error('Failed to start vendor:', error);
        }
    };

    const handleStartCustomer = async () => {
        try {
            await ticketService.startCustomer(customerId, Math.floor(1000 / purchaseRate));
            setCustomerId('');
            setPurchaseRate(1);
        } catch (error) {
            console.error('Failed to start customer:', error);
        }
    };

    const handleStopAll = async () => {
        try {
            await ticketService.stopAll();
        } catch (error) {
            console.error('Failed to stop operations:', error);
        }
    };

    return (
        <Box sx={{ p: 3, bgcolor: 'white', borderRadius: 1, boxShadow: 1 }}>
            <Typography variant="h6" gutterBottom>Control Panel</Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', gap: 2 }}>
                        <TextField
                            label="Vendor ID"
                            value={vendorId}
                            onChange={(e) => setVendorId(e.target.value)}
                            fullWidth
                        />
                        <TextField
                            label="Tickets per second"
                            type="number"
                            value={releaseRate}
                            onChange={(e) => setReleaseRate(Number(e.target.value))}
                            fullWidth
                        />
                        <Button variant="contained" onClick={handleStartVendor}>
                            Start Vendor
                        </Button>
                    </Box>
                </Grid>
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', gap: 2 }}>
                        <TextField
                            label="Customer ID"
                            value={customerId}
                            onChange={(e) => setCustomerId(e.target.value)}
                            fullWidth
                        />
                        <TextField
                            label="Tickets per second"
                            type="number"
                            value={purchaseRate}
                            onChange={(e) => setPurchaseRate(Number(e.target.value))}
                            fullWidth
                        />
                        <Button variant="contained" onClick={handleStartCustomer}>
                            Start Customer
                        </Button>
                    </Box>
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" color="error" onClick={handleStopAll}>
                        Stop All Operations
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}
