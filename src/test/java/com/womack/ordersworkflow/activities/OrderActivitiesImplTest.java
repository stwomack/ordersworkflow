package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.*;
import io.temporal.testing.TestActivityEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class OrderActivitiesImplTest {
    private TestActivityEnvironment testEnvironment;
    private OrderActivities activities;

    @BeforeEach
    public void init() {
        testEnvironment = TestActivityEnvironment.newInstance();
        testEnvironment.registerActivitiesImplementations(new OrderActivitiesImpl());
        activities = testEnvironment.newActivityStub(OrderActivities.class);
    }

    @AfterEach
    void destroy() {
        testEnvironment.close();
    }

    @Test
    void testProcessPayment() {
        Payment payment = new Payment();
        payment.setName("Payment 1234");
        OrderActivityOutput result = activities.processPayment(payment);
        assertEquals("Payment", result.getMessage());
    }


}