package com.womack.ordersworkflow.helpers;

import com.google.gson.Gson;
import com.womack.ordersworkflow.domain.*;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Random;

public class SubmittedOrderHelper {
    public static final Logger LOG = Workflow.getLogger(SubmittedOrderHelper.class);

    public static String generateOrderNumber() {
        Random random = new Random();
        String value = String.valueOf(10000000L + random.nextLong(90000000L));
        LOG.info("Generated OrderNumber: {}", value);
        return value;
    }

    public static SubmittedOrder createSubmittedOrder() {
        Random random = new Random();
        SubmittedOrder order = new SubmittedOrder();
        order.setOrderNumber(generateOrderNumber());
        Payment payment = new Payment();
        payment.setName("PaymentID" + random.nextInt(1000));
        order.setPayment(payment);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setName("Ball Bearings");
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        ArrayList<OrderPackage> orderPackages = new ArrayList<>();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setName("PackagesID: " + random.nextInt(1000));
        orderPackages.add(orderPackage);
        order.setOrderPackages(orderPackages);
        Customer customer = new Customer();
        customer.setName("Duke Womack");
        order.setCustomer(customer);
        LOG.info(new Gson().toJson(order));
        return order;
    }
}
