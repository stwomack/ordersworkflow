package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.OrderActivityOutput;
import com.womack.ordersworkflow.domain.OrderPackage;

import java.util.List;

public class ShippingActivitiesImpl implements ShippingActivities {

    @Override
    public OrderActivityOutput shipPackage(List<OrderPackage> orderPackages) {
        return new OrderActivityOutput("Package Shipped");
    }

}