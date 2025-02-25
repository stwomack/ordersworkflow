package com.womack.ordersworkflow.workflows;

import com.womack.ordersworkflow.domain.*;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrdersWorkflow {
    @WorkflowMethod
    OrderActivityResponse processOrder(SubmittedOrder Order);
}