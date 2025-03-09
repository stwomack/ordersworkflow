package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.OrderActivityOutput;
import com.womack.ordersworkflow.domain.OrderItem;
import com.womack.ordersworkflow.services.OrderActivitiesRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import io.temporal.testing.TestActivityExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

//TODO Learn more about TestActivityExtension and how it's meant to be used
public class OrderActivitiesImplAltTest {
    @Mock
    private static RestTemplate restTemplate;

    @Mock
    private static OrderActivitiesRepositoryService orderActivitiesRepositoryService;

    static OrderActivitiesImpl orderActivitiesImpl;

    @RegisterExtension
    public static final TestActivityExtension activityExtension =
            TestActivityExtension.newBuilder()
                    .setActivityImplementations(orderActivitiesImpl).build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderActivitiesImpl = new OrderActivitiesImpl(orderActivitiesRepositoryService, restTemplate);
    }

//    @Test
    public void testCheckInventory() {
        List<OrderItem> orderItems = List.of(new OrderItem());
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("Inventory Check Passed");
        OrderActivityOutput output = orderActivitiesImpl.checkInventory(orderItems);
        assertEquals("Inventory Check Passed", output.getMessage());
    }
}
