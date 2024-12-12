package com.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;


    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "customer_id")
    private String customerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public boolean isAvailable() {
        return "AVAILABLE".equals(status);
    }

    public void markAsSold(String customerId) {
        this.status = "SOLD";
        this.customerId = customerId;
    }

    public void markAsAvailable(String vendorId) {
        this.status = "AVAILABLE";
        this.vendorId = vendorId;
        this.customerId = null;
    }
}
