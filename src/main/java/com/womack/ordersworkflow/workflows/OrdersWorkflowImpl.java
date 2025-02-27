package com.womack.ordersworkflow.workflows;

import com.womack.ordersworkflow.activities.OrderActivities;
import com.womack.ordersworkflow.domain.*;
import com.womack.ordersworkflow.helpers.SubmittedOrderHelper;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import java.time.Duration;

public class OrdersWorkflowImpl implements OrdersWorkflow {
    public static final Logger LOG = Workflow.getLogger(OrdersWorkflowImpl.class);

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
        LOG.info("Workflow init: {} ", order.toString());
        OrderActivityOutput orderActivityOutput = activities.processPayment(order.getPayment());
        orderActivityOutput.addMessage(activities.checkInventory(order.getOrderItems()).getMessage());
        LOG.info("Tired, going to take a nap");
        Workflow.sleep(Duration.ofSeconds(2)); // YOLO
        LOG.info("I feel refreshed");
        orderActivityOutput.addMessage(activities.shipPackage(order.getOrderPackages()).getMessage());
        orderActivityOutput.addMessage(activities.notifyCustomer(order.getCustomer()).getMessage());
        orderActivityOutput.addMessage("Confirmation Number: " + SubmittedOrderHelper.generateOrderNumber());
        LOG.info("Status: {}", orderActivityOutput.getMessage());
        return orderActivityOutput;
    }

}