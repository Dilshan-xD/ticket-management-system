import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/tickets';

export const ticketService = {
    startVendor: async (vendorId, releaseRate) => {
        const response = await axios.post(`${API_BASE_URL}/vendor/start`, null, {
            params: { vendorId, releaseRate }
        });
        return response.data;
    },

    startCustomer: async (customerId, purchaseRate) => {
        const response = await axios.post(`${API_BASE_URL}/customer/start`, null, {
            params: { customerId, purchaseRate }
        });
        return response.data;
    },

    stopAll: async () => {
        const response = await axios.post(`${API_BASE_URL}/stop`);
        return response.data;
    },

    getStatus: async () => {
        const response = await axios.get(`${API_BASE_URL}/status`);
        return response.data;
    }
};
