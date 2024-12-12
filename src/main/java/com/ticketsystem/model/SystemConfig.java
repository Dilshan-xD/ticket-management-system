package com.ticketsystem.model;

import lombok.Data;
import java.util.List;

@Data
public class SystemConfig {
    private List<VendorConfig> vendors;
    private List<CustomerConfig> customers;
}
