package com.womack.ordersworkflow.activities;


import com.womack.ordersworkflow.domain.Customer;
import com.womack.ordersworkflow.domain.OrderItem;
import com.womack.ordersworkflow.domain.OrderPackage;
import com.womack.ordersworkflow.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.List;

public class OrderActivitiesImpl  implements OrderActivities {
    private String serviceUrl;

    RestTemplate restTemplate = new RestTemplate();
    private final Logger LOG = LoggerFactory.getLogger(OrderActivitiesImpl.class);

    @Override
    public String processPayment(Payment payment) {
        LOG.info("orderprocessingservice-url {} ", serviceUrl);
        return restTemplate.postForObject(serviceUrl + "processPayment", getHttpEntity(payment), String.class);
    }

    @Override
    public String checkInventory(List<OrderItem> orderItems) {
        return restTemplate.postForObject(serviceUrl + "checkInventory", getHttpEntity(orderItems), String.class);
    }

    @Override
    public String shipPackage(List<OrderPackage> orderPackages) {
        return restTemplate.postForObject(serviceUrl + "shipPackage", getHttpEntity(orderPackages), String.class);
    }

    @Override
    public String notifyCustomer(Customer customer) {
        return restTemplate.postForObject(serviceUrl + "notifyCustomer", getHttpEntity(customer), String.class);
    }

    private static HttpEntity<Object> getHttpEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(object, headers);
        return requestEntity;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
