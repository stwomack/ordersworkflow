package com.womack.ordersworkflow.workflows;

import com.womack.ordersworkflow.activities.OrderActivities;
import com.womack.ordersworkflow.domain.*;
import com.womack.ordersworkflow.helpers.SubmittedOrderHelper;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class OrdersWorkflowImplTest {

    private TestWorkflowEnvironment testEnvironment;
    private OrderActivities orderActivities;
    private OrdersWorkflow ordersWorkflow;

    @BeforeEach
    public void setUp() {
        testEnvironment = TestWorkflowEnvironment.newInstance();
        Worker worker = testEnvironment.newWorker("orders-tasks");
        orderActivities = Mockito.mock(OrderActivities.class);
        worker.registerActivitiesImplementations(orderActivities);
        worker.registerWorkflowImplementationTypes(OrdersWorkflowImpl.class);
        testEnvironment.start();

        WorkflowClient workflowClient = testEnvironment.getWorkflowClient();
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue("orders-tasks")
                .build();
        ordersWorkflow = workflowClient.newWorkflowStub(OrdersWorkflow.class, workflowOptions);
    }

    @AfterEach
    public void destroy() {
        testEnvironment.close();
    }

    @Test
    public void testProcessOrder() {
        SubmittedOrder order = new SubmittedOrder();
        order.setOrderItems(new ArrayList<>());
        order.setPayment(new Payment());
        order.setCustomer(new Customer());
        order.setOrderPackages(new ArrayList<>());

        Mockito.when(orderActivities.checkInventory(any())).thenReturn(new OrderActivityOutput("Inventory checked"));
        Mockito.when(orderActivities.processPayment(any(), any())).thenReturn(new OrderActivityOutput("Payment processed"));
        Mockito.when(orderActivities.shipPackage(any())).thenReturn(new OrderActivityOutput("Package shipped"));
        Mockito.when(orderActivities.notifyCustomer(any())).thenReturn(new OrderActivityOutput("Customer notified"));

        try (MockedStatic<SubmittedOrderHelper> mockedHelper = Mockito.mockStatic(SubmittedOrderHelper.class)) {
            mockedHelper.when(SubmittedOrderHelper::generateOrderNumber).thenReturn("12345678");

            OrderActivityOutput actualOutput = ordersWorkflow.processOrder(order);

            String outputMessage = actualOutput.getMessage();
            assertTrue(outputMessage.contains("Inventory checked"));
            assertTrue(outputMessage.contains("Payment processed"));
            assertTrue(outputMessage.contains("Package shipped"));
            assertTrue(outputMessage.contains("Customer notified"));
            assertTrue(outputMessage.contains("Confirmation Number:"));
        }
    }
}