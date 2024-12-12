package com.ticketsystem;

import com.ticketsystem.model.SystemConfig;
import com.ticketsystem.service.CLIService;
import com.ticketsystem.service.TicketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CLIService cliService, TicketService ticketService) {
        return args -> {
            SystemConfig config = cliService.startCLI();

            // Start vendors
            config.getVendors().forEach(vendor ->
                    ticketService.startVendor(vendor.getVendorId(), vendor.getReleaseRate())
            );

            // Start customers
            config.getCustomers().forEach(customer ->
                    ticketService.startCustomer(customer.getCustomerId(), customer.getRetrievalRate())
            );
        };
    }
}
