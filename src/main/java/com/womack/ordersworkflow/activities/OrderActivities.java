package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.Customer;
import com.womack.ordersworkflow.domain.OrderItem;
import com.womack.ordersworkflow.domain.OrderPackage;
import com.womack.ordersworkflow.domain.Payment;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface OrderActivities {
    String processPayment(Payment payment);

    String checkInventory(List<OrderItem> orderItems);

    String shipPackage(List<OrderPackage> orderPackages);

    String notifyCustomer(Customer customer);
}
