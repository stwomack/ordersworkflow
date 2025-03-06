package com.womack.ordersworkflow.workflows;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ShippingWorkflow {

    @WorkflowMethod
    void processShipping(String orderId);

    @ActivityInterface
    interface ShippingActivities {
        @ActivityMethod
        void packageOrder(String orderId);

        @ActivityMethod
        void shipOrder(String orderId);

        @ActivityMethod
        void sendShippingConfirmation(String orderId);
    }
}
