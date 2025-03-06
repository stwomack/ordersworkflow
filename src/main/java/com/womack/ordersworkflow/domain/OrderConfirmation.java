package com.womack.ordersworkflow.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity // Add the @Entity annotation
public class OrderConfirmation {

    @Id // Add the @Id annotation
    private String confirmationNumber;

    @Enumerated(EnumType.STRING) // Store the enum as a String in the database
    private OrderStatus status;

    // No-argument constructor (required by JPA)
    public OrderConfirmation() {
    }

    public OrderConfirmation(String confirmationNumber, OrderStatus status) {
        this.confirmationNumber = confirmationNumber;
        this.status = status;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public enum OrderStatus {
        WORKING,
        COMPLETED,
        FAILED;
    }
}