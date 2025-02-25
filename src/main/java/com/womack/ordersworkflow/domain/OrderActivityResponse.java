package com.womack.ordersworkflow.domain;

public class OrderActivityResponse {
    String message = "";

    public OrderActivityResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

