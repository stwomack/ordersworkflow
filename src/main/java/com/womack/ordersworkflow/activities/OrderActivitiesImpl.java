package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.*;
import com.womack.ordersworkflow.helpers.HttpHelper;
import com.womack.ordersworkflow.services.OrderActivitiesRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OrderActivitiesImpl implements OrderActivities {
    private String serviceUrl;
    private final RestTemplate restTemplate;
    public static final Logger LOG = LoggerFactory.getLogger(OrderActivitiesImpl.class);

    private final OrderActivitiesRepositoryService orderActivitiesRepositoryService;

    public OrderActivitiesImpl(OrderActivitiesRepositoryService orderActivitiesRepositoryService, RestTemplate restTemplate) {
        this.orderActivitiesRepositoryService = orderActivitiesRepositoryService;
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderActivityOutput checkInventory(List<OrderItem> orderItems) {
        String response = restTemplate.postForObject(serviceUrl + "checkInventory", HttpHelper.getHttpEntity(orderItems), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput processPayment(Payment payment) {
        LOG.debug("orderprocessingservice-url {} ", serviceUrl);
        String response = restTemplate.postForObject(serviceUrl + "processPayment", HttpHelper.getHttpEntity(payment), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput shipPackage(List<OrderPackage> orderPackages) {
        String response = restTemplate.postForObject(serviceUrl + "shipPackage", HttpHelper.getHttpEntity(orderPackages), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput notifyCustomer(Customer customer) {
        String response = restTemplate.postForObject(serviceUrl + "notifyCustomer", HttpHelper.getHttpEntity(customer), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public void setStatus(OrderConfirmation orderConfirmation) {
        orderActivitiesRepositoryService.saveOrderConfirmation(orderConfirmation);
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}