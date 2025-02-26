package com.womack.ordersworkflow.domain;

//TODO - Talk about need to do this, since it may be superfluous
public class OrderActivityInput {
    private SubmittedOrder submittedOrder;

    public OrderActivityInput(SubmittedOrder submittedOrder) {
        this.submittedOrder = submittedOrder;
    }

    public SubmittedOrder getSubmittedOrder() {
        return submittedOrder;
    }
}
