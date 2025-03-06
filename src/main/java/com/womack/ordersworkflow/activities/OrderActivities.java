package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.*;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface OrderActivities {
    OrderActivityOutput processPayment(Payment payment);

    OrderActivityOutput checkInventory(List<OrderItem> orderItems);

    OrderActivityOutput notifyCustomer(Customer customer);
}
