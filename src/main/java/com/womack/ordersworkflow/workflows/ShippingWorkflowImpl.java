package com.womack.ordersworkflow.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class ShippingWorkflowImpl implements ShippingWorkflow {

    private final ShippingActivities activities = Workflow.newActivityStub(
            ShippingActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build());

    @Override
    public void processShipping(String orderId) {
        activities.packageOrder(orderId);
        activities.shipOrder(orderId);
        activities.sendShippingConfirmation(orderId);
    }
}
