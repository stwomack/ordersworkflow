package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.Customer;
import com.womack.ordersworkflow.domain.OrderActivityResponse;
import com.womack.ordersworkflow.domain.OrderItem;
import com.womack.ordersworkflow.domain.OrderPackage;
import com.womack.ordersworkflow.domain.Payment;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface OrderActivities {
    OrderActivityResponse processPayment(Payment payment);
    OrderActivityResponse checkInventory(List<OrderItem> orderItems);
    OrderActivityResponse shipPackage(List<OrderPackage> orderPackages);
    OrderActivityResponse notifyCustomer(Customer customer);
}
