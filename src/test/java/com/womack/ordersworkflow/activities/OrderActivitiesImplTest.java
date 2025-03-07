package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.*;
import com.womack.ordersworkflow.services.OrderActivitiesRepositoryService;
import io.temporal.testing.TestActivityEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

class OrderActivitiesImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OrderActivitiesRepositoryService orderActivitiesRepositoryService;

    @InjectMocks
    private OrderActivitiesImpl orderActivitiesImpl;

    private TestActivityEnvironment testActivityEnvironment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testActivityEnvironment = TestActivityEnvironment.newInstance();
        orderActivitiesImpl = new OrderActivitiesImpl(orderActivitiesRepositoryService, restTemplate);
        orderActivitiesImpl.setServiceUrl("http://localhost:8080/");
        testActivityEnvironment.registerActivitiesImplementations(orderActivitiesImpl);
    }

    @Test
    void testCheckInventory() {
        List<OrderItem> orderItems = List.of(new OrderItem());
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("Inventory Check Passed");

        OrderActivities activities = testActivityEnvironment.newActivityStub(OrderActivities.class);
        OrderActivityOutput result = activities.checkInventory(orderItems);

        assertEquals("Inventory Check Passed", result.getMessage());
        verify(restTemplate).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    void testProcessPayment() {
        Payment payment = new Payment();
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("Payment Successful");

        OrderActivities activities = testActivityEnvironment.newActivityStub(OrderActivities.class);
        OrderActivityOutput result = activities.processPayment(payment);

        assertEquals("Payment Successful", result.getMessage());
        verify(restTemplate).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    void testShipPackage() {
        List<OrderPackage> orderPackages = List.of(new OrderPackage());
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("Package Shipped Successfully");

        OrderActivities activities = testActivityEnvironment.newActivityStub(OrderActivities.class);
        OrderActivityOutput result = activities.shipPackage(orderPackages);

        assertEquals("Package Shipped Successfully", result.getMessage());
        verify(restTemplate).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    void testNotifyCustomer() {
        Customer customer = new Customer();
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("Customer Successfully Notified");

        OrderActivities activities = testActivityEnvironment.newActivityStub(OrderActivities.class);
        OrderActivityOutput result = activities.notifyCustomer(customer);

        assertEquals("Customer Successfully Notified", result.getMessage());
        verify(restTemplate).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    void testSetStatus() {
        OrderConfirmation orderConfirmation = new OrderConfirmation();

        orderActivitiesImpl.setStatus(orderConfirmation);

        verify(orderActivitiesRepositoryService).saveOrderConfirmation(orderConfirmation);
    }
}