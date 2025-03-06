package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.workflows.ShippingWorkflow;
import com.womack.ordersworkflow.workflows.ShippingWorkflow.ShippingActivities;


public class ShippingActivitiesImpl implements ShippingWorkflow.ShippingActivities {

    @Override
    public void packageOrder(String orderId) {
        System.out.println("Packaging order: " + orderId);
    }

    @Override
    public void shipOrder(String orderId) {
        System.out.println("Shipping order: " + orderId);
    }

    @Override
    public void sendShippingConfirmation(String orderId) {
        System.out.println("Sending shipping confirmation for order: " + orderId);
    }
}