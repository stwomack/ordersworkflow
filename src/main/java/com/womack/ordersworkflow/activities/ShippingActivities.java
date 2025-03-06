package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.OrderActivityOutput;
import com.womack.ordersworkflow.domain.OrderPackage;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import java.util.List;

@ActivityInterface
public interface ShippingActivities {

    @ActivityMethod
    OrderActivityOutput shipPackage(List<OrderPackage> orderPackages);

}
