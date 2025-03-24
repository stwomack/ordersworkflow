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
    ActivityOptions orderActivityOptions;

    @Override
    public OrderActivityOutput processOrder(SubmittedOrder order) {
        RetryOptions retryOptions = RetryOptions.newBuilder()
                .setInitialInterval(Duration.ofSeconds(2))
                .setMaximumInterval(Duration.ofSeconds(120))
                .build();

        orderActivityOptions = ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(5))
                .setRetryOptions(retryOptions)
                .build();
        OrderActivities orderActivities = Workflow.newActivityStub(OrderActivities.class, orderActivityOptions);
        LOG.info("Workflow init: {} ", order.toString());
        String confirmationNumber = orderActivities.generateOrderNumber();
        OrderActivityOutput orderActivityOutput = orderActivities.checkInventory(order.getOrderItems());
        orderActivityOutput.addMessage(orderActivities.processPayment(confirmationNumber, order.getPayment()).getMessage());
        LOG.info("Tired, going to take a nap");
        Workflow.sleep(Duration.ofSeconds(2)); // YOLO
        LOG.info("I feel refreshed");
        orderActivityOutput.addMessage(orderActivities.shipPackage(order.getOrderPackages()).getMessage());
        orderActivityOutput.addMessage(orderActivities.notifyCustomer(order.getCustomer()).getMessage());
        orderActivityOutput.addMessage("Confirmation Number: " + confirmationNumber);
        LOG.info("Status: {}", orderActivityOutput.getMessage());
        return orderActivityOutput;
    }
}