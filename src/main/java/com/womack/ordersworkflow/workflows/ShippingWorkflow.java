package com.womack.ordersworkflow.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ShippingWorkflow {

    @WorkflowMethod
    void processShipping(String orderId);

}
