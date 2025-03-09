package com.womack.ordersworkflow.services;

import com.womack.ordersworkflow.domain.OrderConfirmation;
import com.womack.ordersworkflow.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderActivitiesRepositoryService {

    @Autowired
    OrderStatusRepository orderStatusRepository;

    public OrderActivitiesRepositoryService(OrderStatusRepository orderStatusRepository){
        this.orderStatusRepository = orderStatusRepository;
    }

    public OrderActivitiesRepositoryService() {
    }

    public void saveOrderConfirmation(OrderConfirmation orderConfirmation) {
        orderStatusRepository.save(orderConfirmation);
    }

    public String getStatus(String confirmationNumber) {
        return orderStatusRepository.findById(confirmationNumber).toString();
    }
}
