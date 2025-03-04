package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.*;
import io.temporal.testing.TestActivityEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderActivitiesImplTest {
    private TestActivityEnvironment testEnvironment;
    private OrderActivities activities;
    private OrderActivitiesImpl orderActivitiesImpl;

    @BeforeEach
    public void init() {
        testEnvironment = TestActivityEnvironment.newInstance();
        orderActivitiesImpl = Mockito.mock(OrderActivitiesImpl.class);
        testEnvironment.registerActivitiesImplementations(orderActivitiesImpl);
        activities = testEnvironment.newActivityStub(OrderActivities.class);
    }

    @AfterEach
    void destroy() {
        testEnvironment.close();
    }

    @Test
    void testProcessPayment() {
        String paymentReturnValue = "Payment 1234";
        Payment payment = new Payment();
        payment.setName(paymentReturnValue);

        OrderActivityOutput orderActivityOutput = new OrderActivityOutput(paymentReturnValue);
        when(orderActivitiesImpl.processPayment(payment)).thenReturn(orderActivityOutput);

        OrderActivities mockActivities = mock(OrderActivities.class);
        when(mockActivities.processPayment(payment)).thenReturn(orderActivityOutput);

        OrderActivityOutput result = mockActivities.processPayment(payment);
        assertEquals(paymentReturnValue, result.getMessage());

        verify(mockActivities, times(1)).processPayment(payment);
    }
}