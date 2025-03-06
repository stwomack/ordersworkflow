package com.womack.ordersworkflow.repositories;

import com.womack.ordersworkflow.domain.OrderConfirmation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderConfirmation, String> {

}
