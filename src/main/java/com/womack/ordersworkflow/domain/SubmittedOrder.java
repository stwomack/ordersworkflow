package com.womack.ordersworkflow.domain;

import com.womack.ordersworkflow.helpers.SubmittedOrderHelper;

import java.util.List;

public class SubmittedOrder {
    String orderNumber;
    Payment payment;
    List<OrderItem> orderItems;
    List<OrderPackage> orderPackages;
    Customer customer;

    public SubmittedOrder() {
    }

    public String getOrderNumber() {
        return orderNumber;
    };

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
    }
}
