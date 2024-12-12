package com.ticketsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketsystem.model.SystemConfig;
import com.ticketsystem.model.VendorConfig;
import com.ticketsystem.model.CustomerConfig;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CLIService {
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path configPath = Path.of("system-config.json");

    public SystemConfig startCLI() {
        if (Files.exists(configPath)) {
            System.out.println("Previous configuration found. Would you like to use it? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                return loadConfig();
            }
        }

        return createNewConfig();
    }

    private SystemConfig createNewConfig() {
        SystemConfig config = new SystemConfig();

        System.out.println("Enter Vendor Count:");
        int vendorCount = Integer.parseInt(scanner.nextLine());

        List<VendorConfig> vendors = new ArrayList<>();
        for (int i = 0; i < vendorCount; i++) {
            VendorConfig vendor = new VendorConfig();
            System.out.println("Enter Vendor " + (i+1) + " ID:");
            vendor.setVendorId(scanner.nextLine());
            System.out.println("Enter tickets per second for Vendor " + (i+1) + ":");
            int ticketsPerSecond = Integer.parseInt(scanner.nextLine());
            // Convert tickets per second to release rate in milliseconds
            vendor.setReleaseRate(1000 / ticketsPerSecond);
            vendors.add(vendor);
        }

        System.out.println("Enter Customer Count:");
        int customerCount = Integer.parseInt(scanner.nextLine());

        List<CustomerConfig> customers = new ArrayList<>();
        for (int i = 0; i < customerCount; i++) {
            CustomerConfig customer = new CustomerConfig();
            System.out.println("Enter Customer " + (i+1) + " ID:");
            customer.setCustomerId(scanner.nextLine());
            System.out.println("Enter tickets per second for Customer " + (i+1) + ":");
            int ticketsPerSecond = Integer.parseInt(scanner.nextLine());
            // Convert tickets per second to retrieval rate in milliseconds
            customer.setRetrievalRate(1000 / ticketsPerSecond);
            customers.add(customer);
        }

        config.setVendors(vendors);
        config.setCustomers(customers);
        saveConfig(config);

        return config;
    }

    private void saveConfig(SystemConfig config) {
        try {
            objectMapper.writeValue(configPath.toFile(), config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }

    private SystemConfig loadConfig() {
        try {
            return objectMapper.readValue(configPath.toFile(), SystemConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
}
