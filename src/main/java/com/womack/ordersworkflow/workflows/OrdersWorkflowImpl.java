package com.womack.ordersworkflow.workflows;

import com.womack.ordersworkflow.activities.OrderActivities;
import com.womack.ordersworkflow.domain.*;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrdersWorkflowImpl implements OrdersWorkflow {
    private final Logger LOG = LoggerFactory.getLogger(OrdersWorkflowImpl.class);

    RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(2))
            .setMaximumInterval(Duration.ofSeconds(120))
            .setBackoffCoefficient(2.0)
            .setMaximumAttempts(50)
            .build();

    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryOptions)
            .build();

    private final OrderActivities activities = Workflow.newActivityStub(OrderActivities.class, options);

    @Override
    public OrderActivityOutput processOrder(SubmittedOrder order) {
        LOG.info("Workflow init: " + order.toString());
        OrderActivityOutput paymentStatus = activities.processPayment(order.getPayment());
        OrderActivityOutput inventoryStatus = activities.checkInventory(order.getOrderItems());
        OrderActivityOutput packageStatus = activities.shipPackage(order.getOrderPackages());
        OrderActivityOutput notificationStatus = activities.notifyCustomer(order.getCustomer());
        String returnMessage = String.join(" -- ", paymentStatus.getMessage(), inventoryStatus.getMessage(), packageStatus.getMessage(), notificationStatus.getMessage());
        LOG.info(String.format("%s%s", "Status: ", returnMessage));
        return new OrderActivityOutput(returnMessage);
    }

}