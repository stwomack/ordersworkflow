package com.womack.ordersworkflow.domain;

import java.util.List;

public class SubmittedOrder {
    Payment payment;
    List<OrderItem> orderItems;
    List<OrderPackage> orderPackages;
    Customer customer;

    public SubmittedOrder() {
    }

    public SubmittedOrder(Payment payment, List<OrderItem> orderItems, List<OrderPackage> orderPackages, Customer customer) {
        this.payment = payment;
        this.orderItems = orderItems;
        this.orderPackages = orderPackages;
        this.customer = customer;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
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
}
